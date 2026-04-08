package com.example.nhom4_w10;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.example.nhom4_w2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNav;
    private FloatingActionButton fab;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_w10);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNav = findViewById(R.id.bottom_navigation);
        fab = findViewById(R.id.fab);
        navView = findViewById(R.id.nav_view);

        // Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initial Fragment
        loadFragment(TabsContainerFragment.newInstance("Home"));

        // Bottom Navigation
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                loadFragment(TabsContainerFragment.newInstance("Home"));
            } else if (item.getItemId() == R.id.nav_settings) {
                loadFragment(TabsContainerFragment.newInstance("Settings"));
            }
            return true;
        });

        // Floating Action Button
        fab.setOnClickListener(v -> {
            NotificationService.showNotification(this, "Message: Now", System.currentTimeMillis());

            new Handler().postDelayed(() -> {
                long threeMinutesAgo = System.currentTimeMillis() - (3 * 60 * 1000);
                NotificationService.showNotification(this, "Message: 3 minutes ago", threeMinutesAgo);
            }, 5000); 
        });

        // Drawer Item Clicks
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.drawer_home) {
                bottomNav.setSelectedItemId(R.id.nav_home);
            } else if (id == R.id.drawer_settings) {
                bottomNav.setSelectedItemId(R.id.nav_settings);
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
