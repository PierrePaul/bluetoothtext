package com.pierrepaul.bluetoothtext;

import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class bluetoothtext extends Activity implements TextToSpeech.OnInitListener {
	 private static final String TAG = "BlueToothText";

	    private TextToSpeech mTts;
	    private Button mAgainButton;
	    private int currentIndex = 0;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);

	        mTts = new TextToSpeech(this, this);
	        mAgainButton = (Button) findViewById(R.id.again_button);
	        mAgainButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                sayHello();
	            }
	        });
	    }

	    @Override
	    public void onDestroy() {
	        // Don't forget to shutdown!
	        if (mTts != null) {
	            mTts.stop();
	            mTts.shutdown();
	        }

	        super.onDestroy();
	    }

	    // Implements TextToSpeech.OnInitListener.
	    public void onInit(int status) {
	        if (status == TextToSpeech.SUCCESS) {
	            int result = mTts.setLanguage(Locale.CANADA_FRENCH);
	            //int result = mTts.setLanguage(Locale.CANADA);
	            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	               // Language data is missing or the language is not supported.
	                Log.e(TAG, "Language is not available.");
	            }
	        }else{
	        	CharSequence error = "Une erreur est survenue lors de l'initialisation du TextToSpeech.";
	        	showMessage(error);
	        }
	    }

	    private static final String[][] MESSAGES = {
	    	{
				"Bonjour Pierre Paul, comment allez-vous?",
				"Vous semblez légèrement stressé aujourd'hui, que puis-je faire pour vous aidez?",
				"L'essence semble légèrement basse.",
				"La station d'essence la plus proche semble être à 12km.",
				"Comment s'appelle votre invitée?",
				"Bonjour Véronique, j'espère que tu auras beaucoup de plaisir à bord de la mazda 3."
	    	},
	    	{
	    		"Hi Pierre, how are you?",
	    		"You seem a bit stressed today, how my I help you?",
	    		"The gas seem a bit low.",
	    		"The next gas station is currently at 12km from here.",
	    		"What is the name of your guest?",
	    		"Hi Maria, I hope you enjoy the ride."
	    	}
	    };

	    private void sayHello() {
	    	int firstIndex = 1;
	    	Log.e(mTts.getLanguage());
			if(mTts.getLanguage() == Locale.FRANCE)
				firstIndex = 0;
			else
				Log.e(TAG,"Reverting to default language");
			
	    	
	    	if(currentIndex == MESSAGES[firstIndex].length){
	    		CharSequence error = "MOUHAHAHHA";
	        	showMessage(error);
	    	}else{
		        String hello = MESSAGES[firstIndex][currentIndex];
		        mTts.speak(hello, TextToSpeech.QUEUE_FLUSH, null);
		        currentIndex++;
	    	}
	    }
	    
	    private void showMessage(CharSequence message){
	    	int duration = Toast.LENGTH_SHORT;
	    	Context context = getApplicationContext();
        	Toast toast = Toast.makeText(context, message, duration);
        	toast.show();
	    }

	}
