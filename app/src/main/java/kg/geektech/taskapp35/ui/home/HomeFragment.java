package kg.geektech.taskapp35.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.ui.models.News;

public class HomeFragment extends Fragment {

    private NewsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
       adapter.setOnItemClickListeners(pos -> {
           News news= adapter.getItem(pos);
           if (news.getEmail()!=null && news.getEmail().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail())){
                openFragment(news);
           }else {
               Bundle bundle = new Bundle();
               bundle.putSerializable("news",news);
               NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
               navController.navigate(R.id.newsNewFragment, bundle);
           Toast.makeText(requireActivity(), news.getId(), Toast.LENGTH_SHORT).show();
            FirebaseFirestore.getInstance().collection("news").document(news.getId())
                    .update("view_count", FieldValue.increment(1));
       }});
        readDataLive();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> openFragment(null));

     //   getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), (requestKey, result) -> {
        //    News news= (News) result.getSerializable("news");
          // Log.e("Home","text = "+ news.getTitle());
            //adapter.addItem(news);
        //});
    initList();
    }
    private  void  initList(){
        recyclerView.setAdapter(adapter);
       // ((LinearLayoutManager)recyclerView.getLayoutManager()).setReverseLayout();

    }
    private  void  openFragment(News news){
        Bundle bundle = new Bundle();
        bundle.putSerializable("news",news);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
    navController.navigate(R.id.newsFragment, bundle);
    }
    private void readData(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("news").orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(10)
                .get().addOnSuccessListener(snapshots -> {
                    List<News> list = snapshots.toObjects(News.class);
                    adapter.addItems(list);
                });

    }
    private  void readDataLive(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news").orderBy("createdAt",Query.Direction.DESCENDING)
                .limit(20).addSnapshotListener((snapshots, error) -> {
                    List<News> list = new ArrayList<>();
                    assert snapshots != null;
                    for (DocumentSnapshot snapshot:  snapshots){
                        News news = snapshot.toObject(News.class);
                        assert news != null;
                        news.setId(snapshot.getId());
                        list.add(news);
                    }
                    adapter.addItems(list);
                });
    }
}

