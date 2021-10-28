package kg.geektech.taskapp35;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId()== R.id.navigation_home ||
            destination.getId()==R.id.navigation_dashboard||
            destination.getId() == R.id.navigation_notifications||
            destination.getId() == R.id.profileFragment){
                navView.setVisibility(View.VISIBLE);
            }else {
                navView.setVisibility(View.GONE);
            }
            if (destination.getId()== R.id.boardFragment){
                Objects.requireNonNull(getSupportActionBar()).hide();
            }  else {
            Objects.requireNonNull(getSupportActionBar()).show();
            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser()==null) {
            navController.navigate(R.id.loginFragment);
        }
        Prefs prefs = new Prefs(this);
        if (!prefs.isBoardShown()) {
            navController.navigate(R.id.boardFragment);
        }else {
            navController.navigateUp();
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}