package com.example.musicplayerapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musicplayerapp.databinding.ActivityMainBinding;
import com.example.musicplayerapp.presentationlayer.Screens.tab_layout.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
//        implements MusicPlayerBottomSheet.MusicPlayerController
{



    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;
  private    ActivityMainBinding activityMainBinding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Binding Layout
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());


        tabLayout =  findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewpager);
        myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

//        Intent i = new Intent(this, MusicPlayerService.class);
//        startService(i);
//        bindService(i, connection, Context.BIND_AUTO_CREATE);


//        getAllAudioAdapter = new getAllAudioAdapter( model ->{
//            // Ye chalega jab song card click hoga
//        //    Toast.makeText(this, "Clicked: " + model.getSongName(), Toast.LENGTH_SHORT).show();
//            Log.d("pathing", "Path In MainActivity: " + model.getPath());
//
//            if (!bound) {
//                Toast.makeText(this, "Service not bound yet", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            // Play selected song
//            musicService.play(model.getPath());
//            Log.d("pathing" ,"Path In Servicess: " +model.getPath());
//            nowPlayingTitle = model.getSongName();
//
//            // Show bottom sheet
//            MusicPlayerBottomSheet sheet = new MusicPlayerBottomSheet();
//            sheet.attachController((MusicPlayerBottomSheet.MusicPlayerController) this); // pass controller
//            sheet.show(getSupportFragmentManager(), "player_sheet");
//
//
//
//        });
//
//        activityMainBinding.getAllAudiosRecylerview.setLayoutManager(new LinearLayoutManager(this));
//        activityMainBinding.getAllAudiosRecylerview.setAdapter(getAllAudioAdapter);
//
//        MusicPlayerRepoImpl repo = new MusicPlayerRepoImpl();
//        List<MusicPlayerModel> audioList = repo.getAllAudio(this);
//        getAllAudioAdapter.submitList(audioList);
//        Log.d("pathing", "Songs fetched: " + audioList.size());
//
////
//        MusicPlayerRepoImpl repos = new MusicPlayerRepoImpl();
//        List<MusicPlayerModel> audioLists = repos.getAllAudio(this);
//        List<FolderModel>  FoldersList =  repo.getAllAudioFoldersWithAudios(this);
//
//        Log.d("MainActivity", "Songs fetched: " + audioLists.size());
////        Log.d("MainActivity", "Folders fetched: " + FoldersList.size());
////    }

    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.d("pathing", "onStart() called → Binding to service...");
//        Intent i = new Intent(this, MusicPlayerService.class);
//        startService(i);
//        bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.d("pathing", "onStop() called → Unbinding service...");
//        if (bound) {
//            unbindService(connection);
//            bound = false;
//        }
    }

//    private  final ServiceConnection connection =  new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            MusicPlayerService.MusicBinder binder = (MusicPlayerService.MusicBinder) iBinder;
//            musicService = binder.getServices();
//            bound = true;
//            Log.d("pathing", "Service Connected");
//
//        }

//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            bound = false;
//        }
//    };
//
//    // ===== Controller impl for BottomSheet =====
//    @Override public boolean isPlaying() { return musicService != null && musicService.isPlaying(); }
//    @Override public void pause() { if (musicService != null) musicService.pause(); }
//    @Override public void resume() { if (musicService != null) musicService.resume(); }
//    @Override public void seekTo(int ms) { if (musicService != null) musicService.seekTo(ms); }
//    @Override public int getDuration() { return musicService != null ? musicService.getDuration() : 0; }
//    @Override public int getCurrentPosition() { return musicService != null ? musicService.getCurrentPosition() : 0; }
//    @Override public String getNowPlayingTitle() { return nowPlayingTitle; }

}