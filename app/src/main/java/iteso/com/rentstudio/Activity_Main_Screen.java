package iteso.com.rentstudio;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_Main_Screen extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public Fragment_Main fragment_main;
    public Fragment_Lessors fragment_lessors;
    public Fragment_Properties fragment_properties;
    TextView mEditProfile, mSettings, mAddProperty, mAddLessor, mAddRent, mLogout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main__screen);

        mEditProfile = findViewById(R.id.drawer_edit_profile);
        mSettings = findViewById(R.id.drawer_settings);
        mAddProperty = findViewById(R.id.drawer_add_property);
        mAddLessor = findViewById(R.id.drawer_add_lessor);
        mAddRent = findViewById(R.id.drawer_add_rent);
        mLogout = findViewById(R.id.drawer_log_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(mViewPager);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Activity_Main_Screen.this, Activity_LogIn.class);
                startActivity(intent);
                finish();
            }
        });

        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main_Screen.this, Activity_Reauthenticate_User.class);
                startActivity(intent);
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Settings.class);
                startActivity(intent);
            }
        });

        mAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Register_Property.class);
                startActivity(intent);
            }
        });

        mAddLessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Register_Lessor.class);
                startActivity(intent);
            }
        });

        mAddRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Register_Rent.class);
                startActivity(intent);
            }
        });
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if(fragment_properties == null){
                        return new Fragment_Properties();
                    }
                    return fragment_properties;
                case 1:
                    if(fragment_main == null){
                        return new Fragment_Main();
                    }
                    return fragment_main;
                case 2:
                    if(fragment_lessors == null){
                        return new Fragment_Lessors();
                    }
                    return fragment_lessors;
                default:
                    return new Fragment_Main();
            }
        };

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "Propiedades";
                case 1: return "Home";
                case 2: return "Arrendadores";
            }
            return null;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 9999){
            if(resultCode == Activity.RESULT_OK){
                int fragment=data.getExtras().getInt("FRAGMENT");
                switch (fragment){
                    case 0:
                        fragment_properties.onActivityResult(requestCode, resultCode, data);
                        break;
                    case 1:
                        fragment_main.onActivityResult(requestCode, resultCode, data);
                        break;
                    case 2:
                        fragment_lessors.onActivityResult(requestCode, resultCode, data);
                        break;



                }
            }
        }

    }
}

