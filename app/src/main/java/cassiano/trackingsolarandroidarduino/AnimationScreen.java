package cassiano.trackingsolarandroidarduino;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AnimationScreen extends AppCompatActivity {
    //Variaveis globais
    View android;
    View arduino;
    View sol;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private View mControlsView;
    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation_screen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        //Para o Delay --> Irá chamar outras funções com espera.
        Handler delay = new Handler();

        //Pega a imagem view
        android = findViewById(R.id.imgAnim);
        arduino = findViewById(R.id.imgArduino);
        sol = findViewById(R.id.imgSol);

        //Deslizar o Android
        efeito(this.mContentView);

        //Seta o delay de 1/2 segundo, para rodar o efeito do Arduino
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                View tstView;
                tstView = findViewById(R.id.fullscreen_content);

                //Deixa Visivel
                arduino.setVisibility(View.VISIBLE);

                //Desliza o Arduino
                efeito2(tstView);

                //Deixa Invisivel
                arduino.setVisibility(View.INVISIBLE);
            }
        }, 600);

        //Seta o delay 1/2 segundo, para rodar o efeito do SOL
        delay.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                View tstView;
                tstView = findViewById(R.id.fullscreen_content);

                //Deixa Visivel
                sol.setVisibility(View.VISIBLE);

                //Desliza o Arduino
                efeito3(tstView);

                //Deixa Invisivel
                sol.setVisibility(View.INVISIBLE);

            }
        },4000);

        //Direcionar para ListView
        delay.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //Direciona para o ListView Customizado
                lista();
            }
        },5500);

                // Set up the user interaction to manually show or hide the system UI.
                mContentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toggle();
                    }
                });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    public void lista()
    {
        //Mostrando resposta em outra Activity
        Intent navegar = new Intent(this,ListaFuncoes.class);
        //Carregando a Activity
        startActivity(navegar);
        //Mata Activity Atual --> Animação
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    public void efeito(View v) {
        AnimationSet animacao1 =(AnimationSet) AnimationUtils.loadAnimation(this,R.anim.baixo_desaparecer);
        AnimationSet animar = animacao1;
        animar.setFillAfter(true);
        android.startAnimation(animar);
    }

    public void efeito2(View v)
    {
        AnimationSet animacao2 =(AnimationSet) AnimationUtils.loadAnimation(this, R.anim.cima_aparecer);
        AnimationSet animar = animacao2;
        animar.setFillBefore(true);
        arduino.startAnimation(animar);
    }

    public void efeito3(View v)
    {
        AnimationSet animacao3 =(AnimationSet) AnimationUtils.loadAnimation(this, R.anim.aparecer);
        AnimationSet animar = animacao3;
        animar.setFillBefore(true);
        sol.startAnimation(animar);
    }

    //Funcao para o delay
    private Runnable rodarArduino = new Runnable()
    {
        @Override
        public void run()
        {

        }
    };

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
