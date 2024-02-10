package com.eugene.tabs;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.eugene.tabs.entity.Engineer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlankFragment3 extends Fragment
{

    public static BlankFragment3 newInstance() {
        return new BlankFragment3();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank3, container, false);

        TableLayout tableLayout = view.findViewById(R.id.tableLayout);

        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.press_start_2p);

        var engineerList = initList();

        int size = 13;

        for (var i : engineerList) {
            TableRow row = new TableRow(requireContext());

            TextView name = new TextView(requireContext());
            name.setText(i.getName());
            name.setPadding(10, 10, 10, 10);
            name.setTextColor(i.getColor());
            name.setTypeface(typeface);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            row.addView(name);
            //
            ImageView imageView = new ImageView(requireContext());
            imageView.setImageResource(i.getImageId());
            imageView.setLayoutParams(new TableRow.LayoutParams(60, 60));
            imageView.setPadding(10, 10, 10, 10);
            row.addView(imageView);
            //
            TextView salary = new TextView(requireContext());
            salary.setText(i.getSalary());
            salary.setPadding(10, 10, 10, 10);
            salary.setTextColor(i.getColor());
            salary.setTypeface(typeface);
            salary.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            row.addView(salary);
            //
            tableLayout.addView(row);
        }

        return view;
    }

    private List<Engineer> initList(){
        List<Engineer> engineerList = new ArrayList<>();
        String dev = "developer";
        engineerList.add(new Engineer("Python-"+dev, "$114k", R.drawable.python, Color.RED));
        engineerList.add(new Engineer("Kotlin-"+dev, "$101k",R.drawable.kotlin, Color.parseColor("#ff00cc")));
        engineerList.add(new Engineer("C-"+dev, "$97k", R.drawable.c,Color.GREEN));
        engineerList.add(new Engineer("C++-"+dev, "$109k",R.drawable.cpp, Color.YELLOW));
        engineerList.add(new Engineer("Java-"+dev, "$107k", R.drawable.java,Color.parseColor("#db8b0b")));
        engineerList.add(new Engineer("JavaScript-"+dev, "$105k",R.drawable.javascript, Color.parseColor("#00ffdd")));

        return engineerList;
    }

}