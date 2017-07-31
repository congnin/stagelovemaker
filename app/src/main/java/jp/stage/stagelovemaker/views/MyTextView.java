package jp.stage.stagelovemaker.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import jp.stage.stagelovemaker.R;
import jp.stage.stagelovemaker.utils.FontManager;
import jp.stage.stagelovemaker.utils.UserTypeFace;

/**
 * Created by congn on 7/31/2017.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    public enum TextStyle {
        MISSION, OPENSANS, BOLD, MEDIUM, REGULAR, SEMIBOLD
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!this.isInEditMode()) { // used for preview while designing.
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextView_TypeFace);
            String Type = a.getString(R.styleable.TextView_TypeFace_TypeFace);
            if (Type == null) {
                UserTypeFace.setRegular(this); //Set Default Font if font is not defined in xml
                return;
            }

            setStyle(Type);
            a.recycle();

        } else {
            setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        }
    }

    private void setStyle(String style) {
        TextStyle value = TextStyle.valueOf(style); //convert String to ENUM
        switch (value) {

            case BOLD:
                UserTypeFace.setBold(this);
                break;
            case MEDIUM:
                UserTypeFace.setMedium(this);
                break;
            case REGULAR:
                UserTypeFace.setRegular(this);
                break;
            case SEMIBOLD:
                UserTypeFace.setSemibold(this);
                break;
            case OPENSANS:
                UserTypeFace.setOpensans(this);
                break;
            case MISSION:
                UserTypeFace.setMission(this);
                break;
        }
    }
}
