package com.tlbail.ptuts3androidapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.EventsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.games.event.Event;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.tlbail.ptuts3androidapp.Model.Achievement.Achievement;
import com.tlbail.ptuts3androidapp.Model.Achievement.Achievements;
import com.tlbail.ptuts3androidapp.R;
import com.tlbail.ptuts3androidapp.Model.Achievement.GoogleAchievementManager;
import com.tlbail.ptuts3androidapp.View.Achievement.AchievementAdaptater;

import java.util.List;

public class AchievementActivity extends AppCompatActivity  {

    // request codes we use when invoking an external activity
    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;


    private static final String TAG = "AchievementActivity";
    private RecyclerView recyclerView;
    private Button buttonShowSucces;
    private GoogleAchievementManager googleAchievementManager;
    private GoogleSignInClient googleSignInClient;
    // Client variables
    private AchievementsClient mAchievementsClient;
    private LeaderboardsClient mLeaderboardsClient;
    private EventsClient mEventsClient;
    private PlayersClient mPlayersClient;

    // The diplay name of the signed in user.
    private String mDisplayName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_achievement);

        bindUI();
        //googleAchievementManager = GoogleAchievementManager.getInstance();
        setupRecyclerView();

        googleSignInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());


    }



    private void bindUI() {
        recyclerView = findViewById(R.id.succesRecyclerView);
        buttonShowSucces = findViewById(R.id.buttonShowSucces);
        buttonShowSucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //googleAchievementManager.showAchievements();

                if(isSignedIn()){
                    onShowAchievementsRequested();
                }else{
                    startSignInIntent();
                }


            }
        });

    }


    private void setupRecyclerView() {
        List<Achievement> achievements = Achievements.getAchivements();
        AchievementAdaptater achievementAdaptater = new AchievementAdaptater(achievements);
        recyclerView.setAdapter(achievementAdaptater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void startSignInIntent() {
        startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    public void onShowAchievementsRequested() {
        mAchievementsClient.getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_UNUSED);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        handleException(e, "achievement exeception");
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //googleAchievementManager.signInSilently(this);
        signInSilently();

    }

    private void signInSilently() {
        Log.d(TAG, "signInSilently()");

        googleSignInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInSilently(): success");
                            onConnected(task.getResult());
                        } else {
                            Log.d(TAG, "signInSilently(): failure", task.getException());
                            onDisconnected();
                        }
                    }
                });

    }


    private void onConnected(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "onConnected(): connected to Google APIs");

        mAchievementsClient = Games.getAchievementsClient(this, googleSignInAccount);
        mLeaderboardsClient = Games.getLeaderboardsClient(this, googleSignInAccount);
        mEventsClient = Games.getEventsClient(this, googleSignInAccount);
        mPlayersClient = Games.getPlayersClient(this, googleSignInAccount);


        // Set the greeting appropriately on main menu
        mPlayersClient.getCurrentPlayer()
                .addOnCompleteListener(new OnCompleteListener<Player>() {
                    @Override
                    public void onComplete(@NonNull Task<Player> task) {
                        String displayName;
                        if (task.isSuccessful()) {
                            displayName = task.getResult().getDisplayName();
                            CharSequence charSequence = new String(("salut " + displayName));
                            Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_LONG).show();

                        } else {
                            Exception e = task.getException();
                            handleException(e, "gogo");
                            displayName = "???";
                        }
                        mDisplayName = displayName;
                    }
                });

        /*
        // if we have accomplishments to push, push them
        if (!mOutbox.isEmpty()) {
            pushAccomplishments();
            Toast.makeText(this, getString(R.string.your_progress_will_be_uploaded),
                    Toast.LENGTH_LONG).show();
        }
        */
        loadAndPrintEvents();
    }

    private void onDisconnected() {
        Log.d(TAG, "onDisconnected()");

        mAchievementsClient = null;
        mLeaderboardsClient = null;
        mPlayersClient = null;
    }

    private boolean isSignedIn(){
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }


    private void handleException(Exception e, String details) {
        int status = 0;

        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            status = apiException.getStatusCode();
        }

        String message = details + status + e;

        new AlertDialog.Builder(AchievementActivity.this)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }
    private void loadAndPrintEvents() {

        final AchievementActivity mainActivity = this;

        mEventsClient.load(true)
                .addOnSuccessListener(new OnSuccessListener<AnnotatedData<EventBuffer>>() {
                    @Override
                    public void onSuccess(AnnotatedData<EventBuffer> eventBufferAnnotatedData) {
                        EventBuffer eventBuffer = eventBufferAnnotatedData.get();

                        int count = 0;
                        if (eventBuffer != null) {
                            count = eventBuffer.getCount();
                        }

                        Log.i(TAG, "number of events: " + count);

                        for (int i = 0; i < count; i++) {
                            Event event = eventBuffer.get(i);
                            Log.i(TAG, "event: "
                                    + event.getName()
                                    + " -> "
                                    + event.getValue());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        handleException(e, "hehe le bug");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(intent);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                onConnected(account);
            } catch (ApiException apiException) {
                String message = apiException.getMessage();
                if (message == null || message.isEmpty()) {
                    message = "getString(R.string.signin_other_error)";
                }

                onDisconnected();

                new AlertDialog.Builder(this)
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
            }
        }
    }

}