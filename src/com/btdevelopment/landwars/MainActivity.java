package com.btdevelopment.landwars;

import com.btdevelopment.landwars.constants.Settings;



import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	//------------------OBJECTS - Start
	

	Button playButton;
	CompoundButton volumeButton;
	ImageButton creditsButton;
	Intent intent;
	MediaPlayer mpButtonClick;
	MediaPlayer mpBackgroundMusic;
	Locale myLocale;
	Spinner languageSpinner;
	SharedPreferences settings;
	AudioManager aManager;
	
	
	//------------------OBJECTS - End
	
	
	

	
	//------------------ACTIVITY (onCreate) - Start
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//------------------DEFINATION OF OBJECTS - Start
		
		volumeButton = (CompoundButton) findViewById(R.id.volumeButton);
		mpBackgroundMusic = MediaPlayer.create(this, R.raw.background_music);
		mpButtonClick = MediaPlayer.create(this, R.raw.button_click );
		playButton = (Button) findViewById(R.id.playButton);
		creditsButton = (ImageButton) findViewById(R.id.creditsButton);
      //languageSpinner = (Spinner) findViewById(R.id.languageSpinner);


		settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		getSettings();
	
		//------------------DEFINATION OF OBJECTS - Finish

		
		
		
		
		
		//------------------LANGUAGE SPINNER - Start

		 
        //String[] content = { "ENG", "TUR" };
 
        

		
		//------------------LANGUAGE SPINNER - End

        
        
        
        
		//------------------BACKGROUND MUSIC - Start
		mpBackgroundMusic.setLooping(true);
		
		if(settings.getBoolean(Settings.VOLUMEBUTTON, true) == true)
			mpBackgroundMusic.start();
		//------------------BACKGROUND MUSIC - End		
		

		
		
		//------------------VOLUME BUTTON - Start
		
		aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
		
		volumeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	            // TODO Auto-generated method stub
	            if (isChecked) {
	                // Sound Notifications is disabled
	            	
	                aManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
	                volumeButton.setBackgroundResource(R.drawable.volume_mute);
	                setSettings_volumeButton(volumeButton.isChecked());
	        		

	                
	                
	            } else {
	                // Sound Notifications is enabled
	                aManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
	                volumeButton.setBackgroundResource(R.drawable.volume_unmute);
	                setSettings_volumeButton(volumeButton.isChecked());
	                mpBackgroundMusic.start();
	            }
	            
            	mpButtonClick.start();

	        }
	    });
		
		
		//------------------VOLUME BUTTON - End
		
		
		
		
		//------------------PLAY BUTTON - Start
				
		playButton.setOnTouchListener(new View.OnTouchListener() {
			
			

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {

	            if(event.getAction() == (MotionEvent.ACTION_UP)){
	            	//Do whatever you want after pressing the button.
	            	Toast.makeText(getApplicationContext(), "Play Me!", Toast.LENGTH_LONG).show(); //Change that. (!!!!!)
	            	
	            	mpButtonClick.start();
	            	
	            	playButton.setBackgroundResource(R.drawable.mainmenu_idle);
	            }
	            else{
	                //Do whatever you want during press.
	            	
	            	playButton.setBackgroundResource(R.drawable.mainmenu_ontouch);
	            }
	            return true;
	        }
	        
	        
	    });
		
		//---------------------PLAY BUTTON - End
		
		
		

		
		
		//---------------------CREDITS BUTTON - Start
						
				creditsButton.setOnTouchListener(new View.OnTouchListener() {

			        @Override
			        public boolean onTouch(View v, MotionEvent event) {

			            if(event.getAction() == (MotionEvent.ACTION_UP)){
			            	//Do whatever you want after pressing the button.
			            	creditsButton.setBackgroundResource(R.drawable.ic_launcher);
			            	
			            	mpButtonClick.start();
			            	
			            	intent = new Intent(MainActivity.this, CreditsActivity.class);
			            	startActivity(intent);
			            }
			            else{
			                //Do whatever you want during press.
			            	
			            	creditsButton.setBackgroundResource(R.drawable.ic_launcher);
			            }
			            return true;
			        }
			    });
				
				
				//---------------------CREDITS BUTTON - End
				
					
		
	}
	//---------------------ACTIVITY (onCreate) - End
	
	//---------------------ACTIVITY (onPause) - Start
	@Override
	protected void onPause() {
		
		super.onPause();
		
		if (this.isFinishing()) {
			mpBackgroundMusic.pause();
		}
		
		Context context = getApplicationContext();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
		
		if (!taskInfo.isEmpty()) {
			
			ComponentName topActivity = taskInfo.get(0).topActivity;
			
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				
				mpBackgroundMusic.pause();
				
			}
			
			else {}
			
		}      
		
	}

	//---------------------ACTIVITY (onPause) - End

	
	//---------------------ACTIVITY (onResume) - Start
	
	@Override
	public void onResume()
	{
	    super.onResume();
	    
	    if(settings.getBoolean(Settings.VOLUMEBUTTON, true) != true)
	    	mpBackgroundMusic.start();
	}
	
	
	//---------------------ACTIVITY (onResume) - End
	
	
	//---------------------ACTIVITY (onStart) - Start
	@Override
	public void onStart()
	{
	    super.onStart();
	    
	    if(settings.getBoolean(Settings.VOLUMEBUTTON, true) != true)
	    	mpBackgroundMusic.start();
	}
	
	//---------------------ACTIVITY (onStart) - End
	
	//---------------------ACTIVITY (onRestart) - Start

	@Override
	public void onRestart()
	{
	    super.onRestart();
	    
	    if(settings.getBoolean(Settings.VOLUMEBUTTON, true) != true)
	    	mpBackgroundMusic.start();
	}
	
	
	//---------------------ACTIVITY (onRestart) - End


	
	//---------------------SETTINGS (Set) - Start
	
public void setSettings_languageSpinner (String language) {
		
		SharedPreferences.Editor e = settings.edit();
		
		e.putString(Settings.LANGUAGESPINNER, language);
			
		e.commit();
	}
	
	
	public void setSettings_volumeButton (boolean volume) {
		
		SharedPreferences.Editor e = settings.edit();
		
		e.putBoolean(Settings.VOLUMEBUTTON, volume);
			
		e.commit();
	}
	
	//---------------------SETTINGS (Set) - End
	
	//---------------------SETTINGS (Get) - Start


	public void getSettings(){
		
		
		
		if (settings.getBoolean(Settings.VOLUMEBUTTON, true) == true){
			
			volumeButton.setBackgroundResource(R.drawable.volume_mute);
			volumeButton.setChecked(settings.getBoolean(Settings.VOLUMEBUTTON, true));
			//mpBackgroundMusic.pause(); 
			
		} 
		
		else{
			volumeButton.setBackgroundResource(R.drawable.volume_unmute);  
			volumeButton.setChecked(settings.getBoolean(Settings.VOLUMEBUTTON, false));
			mpBackgroundMusic.start();
		}		
		
	}
	
	//---------------------SETTINGS (Get) - End
}

			