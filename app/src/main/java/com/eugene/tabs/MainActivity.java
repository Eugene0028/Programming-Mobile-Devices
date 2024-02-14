package com.eugene.tabs;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eugene.tabs.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private List<Fragment> fragmentList = List.of(BlankFragment1.newInstance(), BlankFragment2.newInstance(), BlankFragment3.newInstance());//Arrays.asList(BlankFragment1.newInstance(), BlankFragment2.newInstance(), BlankFragment3.newInstance());

    private List<String> words = List.of("About me", "About programming", "About salaries");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragmentList);
        binding.viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, pos) -> tab.setText(words.get(pos))).attach();
    }
}

