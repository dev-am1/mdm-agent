package ir.batna.provider.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.batna.provider.R;

/**
 * Created by Dev_am1 on 5/6/2020
 */
public class Fragment_device extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_device, null);

        TextView tvResolution = v.findViewById(R.id.tvResolution);
        TextView tvScreenSize = v.findViewById(R.id.tvScreenSize);
        TextView tvDensity = v.findViewById(R.id.tvDensity);
        TextView tvSerialNumber = v.findViewById(R.id.tvSerialNumber);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        float densityDpi = (dm.density * 160f);

        tvResolution.setText(dm.heightPixels + " * " + dm.widthPixels);
        tvScreenSize.setText(Double.toString(screenInches));
        tvDensity.setText(densityDpi + " " + "DPI");
        tvSerialNumber.setText(Build.SERIAL);
        return v;
    }
}
