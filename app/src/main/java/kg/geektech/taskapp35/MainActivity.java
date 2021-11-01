package kg.geektech.taskapp35;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import kg.geektech.taskapp35.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_home ||
                    destination.getId() == R.id.navigation_dashboard ||
                    destination.getId() == R.id.navigation_notifications ||
                    destination.getId() == R.id.profileFragment) {
                binding.navView.setVisibility(View.VISIBLE);
            } else {
                binding.navView.setVisibility(View.GONE);
            }
//            if (destination.getId() == R.id.boardFragment) {
//                Objects.requireNonNull(getSupportActionBar()).hide();
//            } else {
//                Objects.requireNonNull(getSupportActionBar()).show();
//            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            navController.navigate(R.id.loginFragment);
        }
        Prefs prefs = new Prefs(this);
        if (!prefs.isBoardShown()) {
            navController.navigate(R.id.boardFragment);
        } else {
            navController.navigateUp();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}