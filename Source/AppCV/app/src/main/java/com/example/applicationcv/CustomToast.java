package com.example.applicationcv;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {

//    public static int height;
//    public static int width;

    public static void create(Activity context, int string_message_id) {
//        Toast toast = Toast.makeText(context, string_message_id, Toast.LENGTH_LONG);
        create(context, context.getResources().getString(string_message_id));
    }

//    public static void creates(Activity context, String s) {
////        Toast toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
//        try {
//            create(context, s);
//        }catch (Exception e){
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }

    //    public void showMessage(String mess) {
//        textToast.setText(mess);
//        toast.show();
//    }
    public static int dpToPixels(int i, Activity context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = ((int) (i * scale + 0.5f));
        return pixels;
    }

    public static void create(Activity context, String text) {
        LayoutInflater inflater = context.getLayoutInflater();
        View layoutToast = inflater.inflate(R.layout.toast, (ViewGroup) context.findViewById(R.id.toast_layout_root));
        TextView textToast = (TextView) layoutToast.findViewById(R.id.text_toast);
//        imgToast = (ImageView) layoutToast.findViewById(R.id.image);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, dpToPixels(50, context));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layoutToast);

//        ShowToasts.getDialog(context)
//                .setOnRunning(new ShowToasts.OnRunning() {
//                    @Override
//                    public void process(Dialog dialog) {
//                    }
//                })
//                .executeAsync();

//        Toast toast = Toast.makeText(context, s, Toast.LENGTH_LONG);


//        if (toast.getView() != null) {
//            View view = toast.getView();
//            view.setBackgroundResource(R.drawable.sharp_toast);
////        if (height >= 1200 && width >= 800) {
////            view.setBackgroundResource(R.drawable.sharp_toast);
////        } else {
////            view.setBackgroundResource(R.drawable.sharp_toast_small);
////        }
//            TextView text = (TextView) view.findViewById(android.R.id.message);
////        text.setTypeface(null, Typeface.BOLD);
//            text.setGravity(Gravity.CENTER);
//            /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
//            text.setTextColor(context.getResources().getColor(R.color.color_bg, context.getTheme()));
//        }
        textToast.setText(text);
        toast.show();
    }

}
