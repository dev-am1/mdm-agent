package ir.batna.provider.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
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
public class Fragment_info extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, null);
        TextView tvBrand;
        TextView tvModel;
        TextView tvMemory;
        TextView tvAndroidVersion;

        tvBrand = v.findViewById(R.id.tvbrand);
        tvModel = v.findViewById(R.id.tvModel);
        tvMemory = v.findViewById(R.id.tvMemory);
        tvAndroidVersion = v.findViewById(R.id.tvAndroidVersion);
        StatFs stat = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        long mb = 1024 * 1024;
        long size = stat.getTotalBytes() / mb;

        tvBrand.setText(Build.BRAND);
        tvModel.setText(Build.MODEL);
        tvMemory.setText(size + " مگابایت");
        tvAndroidVersion.setText(Build.VERSION.RELEASE);
        return v;
    }
}
