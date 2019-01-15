package com.example.wael.mycart;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.location.Location.distanceBetween;

public class LivreurFragmenttt extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Livreur> livreurList;

    public Fragment newInstance() {
        Log.d("recycler","-----------------------creating new instance---------------------------");
        return new LivreurFragmenttt();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("ProduitView","******************************Start**************************");
        final View view = inflater.inflate(R.layout.livraison_layout, container , false);
        recyclerView = view.findViewById(R.id.livreur_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        Double clientLat = null;
        Double clientLon = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            clientLat= bundle.getDouble("lat");
            clientLon= bundle.getDouble("lon");
        }
        livreurList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collLivreur = db.collection("/livreur");
        final Double finalClientLat = clientLat;
        final Double finalClientLon = clientLon;

        collLivreur
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("recycler creqte","-----------------------creating new instance---------------------------");
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("ProduitView", "******************************success****************************************");
                                Log.d("****", document.getId() + " => " + document.getData());
                                Livreur livreur = document.toObject(Livreur.class);
                                if(livreurValide(livreur, finalClientLat, finalClientLon))
                                {
                                    livreurList.add(livreur);
                                }
                                Log.d("fds", livreurValide(livreur, finalClientLat, finalClientLon).toString());
                            }
                            Log.d("ProduitView", "******************************data collected****************************************\n"+ livreurList);
                            recyclerViewAdapter = new RecyclerViewAdapter(livreurList);
                            recyclerView.setAdapter(recyclerViewAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            return;
                        }
                    }
                });
        Log.d("ProduitView", "******************************Done****************************************");
        return view;
    }

    public Boolean livreurValide(Livreur livreur, Double clientLat, Double clientLon){
        float[] result= new float[3];
        distanceBetween(livreur.getLocation().getLatitude(), livreur.getLocation().getLongitude(),clientLat, clientLon, result);
        Log.d("distance", "******************************distance"+result[0]+"****************************************");
        return result[0]<12000;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView image;
        private RatingBar rating;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.card_livreur, container, false));

            name=(TextView)itemView.findViewById(R.id.textView);
            image=(ImageView)itemView.findViewById(R.id.imageView);
            rating=(RatingBar)itemView.findViewById(R.id.ratingBar);


        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<LivreurFragmenttt.RecyclerViewHolder>{

        private List<Livreur> mlist;

        public RecyclerViewAdapter(List<Livreur> list) {
            this.mlist = list;
        }

        public void setMlist(List<Livreur> mlist) {
            this.mlist = mlist;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new LivreurFragmenttt.RecyclerViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
            holder.name.setText(mlist.get(position).getNom()+" "+mlist.get(position).getPrenom());
            Picasso.get().load(mlist.get(position).getImage()).into(holder.image);
            holder.rating.setRating(mlist.get(position).getRating());
        }

        @Override
        public int getItemCount() {
            return mlist.size() ;
        }

    }

}
