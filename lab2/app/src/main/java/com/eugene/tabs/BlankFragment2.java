package com.eugene.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class BlankFragment2 extends Fragment {


    public static BlankFragment2 newInstance() {
        return new BlankFragment2();
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);

        ListView listView = view.findViewById(R.id.my_list_view);
        List<String> arr = List.of("Android Studio", "Java", "Kotlin", "Computer Programming", "Computer Science", "Algorithms and Data Structures", "OOP", "Memes :D" );
        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.press_start_2p);
        CustomAdapter arrayAdapter = new CustomAdapter(requireActivity(), android.R.layout.simple_list_item_1, arr, typeface);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, v, i, l) -> {
            switch (i) {
                case 0 ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Android_Studio")));
                case 1 ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Java")));
                case 2 ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Kotlin")));
                case 3 ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Computer_programming")));
                case 4 ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Computer_Science")));
                case 5 ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/List_of_data_structures")));
                case 6 ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Object-oriented_programming")));
                case 7 ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@UnusualVideos/videos")));
            }
        });

        return view;
    }
}

