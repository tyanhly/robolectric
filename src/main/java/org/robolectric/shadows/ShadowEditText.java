package org.robolectric.shadows;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.EditText;
import org.robolectric.internal.Implementation;
import org.robolectric.internal.Implements;

/**
 * A shadow for EditText that provides support for listeners
 */
@SuppressWarnings({"UnusedDeclaration"})
@Implements(value = EditText.class, inheritImplementationMethods = true)
public class ShadowEditText extends ShadowTextView {

    private int maxLength = Integer.MAX_VALUE;

    public ShadowEditText() {
        focusable = true;
        focusableInTouchMode = true;
    }

    @Override
    @Implementation(i18nSafe = true)
    public void setText(CharSequence str) {
        if (!TextUtils.isEmpty(str) && str.length() > maxLength) {
            str = str.subSequence(0, maxLength);
        }
        super.setText(str);
    }

    @Override
    @Implementation
    public Editable getText() {
        CharSequence text = super.getText();
        if (!(text instanceof Editable)) {
            return new SpannableStringBuilder(text);
        }
        return (Editable) text;
    }

    @Override
    @Implementation
    public void setSelection(int index) {
        super.setSelection(index);
    }

    @Override
    @Implementation
    public void setSelection(int start, int end) {
        super.setSelection(start, end);
    }

    @Implementation
    public void selectAll() {
        CharSequence text = super.getText();
        super.setSelection(0, text.length() - 1);
    }

}