package com.elhalawany.twittercounter

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.elhalawany.twittercounter.databinding.ComponenetTwitterCounterBinding
import com.twitter.twittertext.TwitterTextParser

class TwitterCounterComponent : FrameLayout {
    private val binding by lazy { ComponenetTwitterCounterBinding.inflate(LayoutInflater.from(context), this, false) }


    fun setMaxCharacterCount(maxCharacterCount: Int) {
        this.maxCharacterCount = maxCharacterCount
    }

    fun setText(text: String) {
        binding.etTypingArea.setText(text)
    }

    fun setHint(text: String) {
        binding.etTypingArea.setHint(text)
    }

    fun getText() = binding.etTypingArea.text.toString()

    fun clearText() {
        binding.etTypingArea.setText("")
    }

    fun isValidPost(): Boolean {
        return typedCharactersCount in 1..maxCharacterCount;
    }

    fun setOnTextChangeListeners(onTextChangeListener: (text: String) -> Unit) {
        this.onTextChangeListener = onTextChangeListener
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {

        val xmlAttr =
            context.obtainStyledAttributes(attrs, R.styleable.TwitterCounterComponent, defStyle, 0)

        maxCharacterCount = xmlAttr.getInteger(R.styleable.TwitterCounterComponent_maxCharacterCount, maxCharacterCount)
        hint = xmlAttr.getString(R.styleable.TwitterCounterComponent_hint) ?: hint
        xmlAttr.recycle()

        binding.etTypingArea.setHint(hint)
        addView(binding.root)

        val message = binding.etTypingArea.text.toString()
        updateCharactersTrackers(message)
        addEditTextListener()
    }

    private fun addEditTextListener() {
        binding.etTypingArea.doOnTextChanged { text, _, _, _ ->
            updateCharactersTrackers(text.toString())
            onTextChangeListener?.invoke(text.toString())
        }
    }

    private fun updateCharactersTrackers(text: String) {
        typedCharactersCount = getCharactersCount(text)
        binding.tvRemainingCharacters.text = (maxCharacterCount - typedCharactersCount).toString();
        binding.tvTypedCharacters.text = "${typedCharactersCount}/${maxCharacterCount}"
        if (typedCharactersCount > maxCharacterCount) {
            binding.tvRemainingCharacters.setTextColor(Color.RED)
            binding.tvTypedCharacters.setTextColor(Color.RED)
        } else {
            binding.tvRemainingCharacters.setTextColor(Color.BLACK)
            binding.tvTypedCharacters.setTextColor(Color.BLACK)
        }
    }

    private fun getCharactersCount(text: String)
    = TwitterTextParser.parseTweet(text).weightedLength;


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private var maxCharacterCount: Int = 280
    private var hint: String = context.getString(R.string.start_typing_you_can_enter_up_to_280_characters)
    private var typedCharactersCount = 0
    private var onTextChangeListener: ((text: String) -> Unit)? = null;
}