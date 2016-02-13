package com.nishiguchimaika.fingerpaint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

public class TestDialogFragment extends DialogFragment {

    int pic;
    int opa;
    int sat;
    float val;
    int num;

    DialogEventListener listener;
    void setOnDialogEventListener(DialogEventListener listener){
        this.listener = listener;
    }
    interface DialogEventListener{
        void setSize(int size);
        void setColor(int color);
        void setOpa(int opa1);
        void setSat(int sat1);
        void setVal(float val1);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState){
        Bundle bundle = getArguments();
        pic = bundle.getInt("pic",0);
        opa = bundle.getInt("opa",0);
        sat = bundle.getInt("sat",0);
        val = bundle.getFloat("val",0);
        num = bundle.getInt("num",0);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.dialog_setting,null);
        builder.setView(content);
        final ColorPicker p = (ColorPicker)content.findViewById(R.id.picker);
        OpacityBar o = (OpacityBar)content.findViewById(R.id.opacitybar);
        p.addOpacityBar(o);
        SaturationBar s = (SaturationBar)content.findViewById(R.id.saturationbar);
        p.addSaturationBar(s);
        ValueBar v = (ValueBar)content.findViewById(R.id.valuebar);
        p.addValueBar(v);
        //p.getColor();
        p.setColor(pic);
        o.setOpacity(opa);
        s.setSaturation(sat);
        v.setValue(val);

        p.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int i) {
                listener.setColor(i);
                //mPaint.setColor(p.getColor());
            }
        });
        o.setOnOpacityChangedListener(new OpacityBar.OnOpacityChangedListener() {
            @Override
            public void onOpacityChanged(int i) {
                listener.setOpa(i);
            }
        });
        s.setOnSaturationChangedListener(new SaturationBar.OnSaturationChangedListener() {
            @Override
            public void onSaturationChanged(int i) {
                listener.setSat(i);
            }
        });
        v.setOnValueChangedListener(new ValueBar.OnValueChangedListener() {
            @Override
            public void onValueChanged(int i) {
                listener.setVal(i);
            }
        });
        final SeekBar sb0 = (SeekBar)content.findViewById(R.id.SeekBar00);
        final TextView tv0 = (TextView)content.findViewById(R.id.TextView00);
        tv0.setText("サイズ: " + sb0.getProgress());
        sb0.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //ドラッグ
                        tv0.setText("サイズ:" + sb0.getProgress());
                        listener.setSize(sb0.getProgress());
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //触れた
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //離した
                    }
                }
        );

        builder.setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        return builder.create();
    }
}