package com.example.nhom4_w10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.nhom4_w2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabsContainerFragment extends Fragment {
    private String name;

    public static TabsContainerFragment newInstance(String name) {
        TabsContainerFragment fragment = new TabsContainerFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs_container, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_inner);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager_inner);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return TabContentFragment.newInstance(name + " - SubTab " + (position + 1));
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText("SubTab " + (position + 1));
            if (position == 0) tab.setIcon(android.R.drawable.ic_menu_today);
            else if (position == 1) tab.setIcon(android.R.drawable.ic_menu_camera);
            else tab.setIcon(android.R.drawable.ic_menu_agenda);
        }).attach();

        return view;
    }
}
