package com.example.wael.mycart;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragmentPannier extends Fragment {

    private TextView mTextViewResult;
    private Button mButtonCommander;


    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Produit> mProduit = new ArrayList<>();
    static float mPrix =0;

    public Fragment newInstance(ArrayList <Produit> mList) {
        Log.d("recycler","-----------------------creating new instance---------------------------");
        return new RecyclerFragmentPannier();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduit = (List<Produit>) getArguments().getSerializable("bundle_key");
        Log.d("affiche","********************** pannier ******************"+mProduit);


    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.recycler_view_fragment_pannier, container , false);
        recyclerView = view.findViewById(R.id.recyclerViewPannier);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerViewAdapter = new RecyclerViewAdapter(mProduit);
        recyclerView.setAdapter(recyclerViewAdapter);
        mTextViewResult = view.findViewById(R.id.textViewResult);
        mButtonCommander = view.findViewById(R.id.buttonCommander);
        mTextViewResult.setText(String.valueOf(mPrix));
        mButtonCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ActivityPannier.class);
                getActivity().startActivity(i);
            }
        });
        return view;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTextNom;
        private TextView mTextGt;
        private TextView mTextPrix;
        private ImageView mImageProd;
        private ElegantNumberButton mElegantNumber;
        private FloatingActionButton mFloatingButton;


        private TextView mTextViewResult;
        private Button mButtonCommander;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.cart_view_panier, container, false));

            mImageProd = itemView.findViewById(R.id.imageView);
            mTextNom = itemView.findViewById(R.id.textViewNom);
            mTextGt = itemView.findViewById(R.id.textViewGT);
            mTextPrix = itemView.findViewById(R.id.textViewPrix);
            mElegantNumber = itemView.findViewById(R.id.elegantNumberd);
            mFloatingButton = itemView.findViewById(R.id.buttonDelete);


        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerFragmentPannier.RecyclerViewHolder>{

        private List<Produit> mlist;

        public RecyclerViewAdapter(List<Produit> list) {
            this.mlist = list;
        }

        public void setMlist(List<Produit> mlist) {
            this.mlist = mlist;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerFragmentPannier.RecyclerViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, final int i) {
            double prixLocal = 0;
            Picasso.get().load(mProduit.get(i).convertImage()).into(recyclerViewHolder.mImageProd);
            recyclerViewHolder.mTextNom.setText(mProduit.get(i).getNom());
            String a = (mProduit.get(i).getGout()) + " - " + (mProduit.get(i).getTaille());
            recyclerViewHolder.mTextGt.setText(a);
            recyclerViewHolder.mTextPrix.setText(setPrix(mProduit.get(i).getPrix()));
            recyclerViewHolder.mElegantNumber.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    double prixLocal = mProduit.get(i).getPrix() * newValue;
                    if(oldValue<newValue){
                        mPrix+=mProduit.get(i).getPrix();
                    }else if(oldValue>newValue){
                        mPrix-=mProduit.get(i).getPrix();
                    }
                    mTextViewResult.setText(setPrix(mPrix));
                }
            });
            int number = Integer.valueOf(recyclerViewHolder.mElegantNumber.getNumber());
            recyclerViewHolder.mFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPrix -= mProduit.get(i).getPrix() *  Integer.parseInt(recyclerViewHolder.mElegantNumber.getNumber());
                    mTextViewResult.setText(setPrix(mPrix));
                    mProduit.remove(i);
                    notifyDataSetChanged();
                }
            });

//            mPrix += prixLocal;
//            mTextViewResult.setText(String.valueOf(mPrix));
        }

        @Override
        public int getItemCount() {
            return mProduit.size();
        }

        public String setPrix(float p){
            /*double a=p;
            int i= (int) a;
            a-=i;
            int l=String.valueOf(a).length();
            if(a!=0){
                if(l==4){
                    return String.valueOf(p)+"0  DT";
                }else if(l>4){
                    return String.valueOf(p)+"  DT";
                }
            }else{
                return String.valueOf(p)+"000  DT";
            }
            return String.valueOf(p)+"00  DT";*/
            return String.format("%.3f",p)+"DT";
        }
    }

}
