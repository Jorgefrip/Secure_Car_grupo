package co.edu.unab.secure_car;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class adaptadorCarro extends RecyclerView.Adapter<adaptadorCarro.ViewHolder> implements View.OnClickListener{
    private int resource;
    private ArrayList<Carros> listadoCarros;
    private View.OnClickListener listener;

    public adaptadorCarro(ArrayList<Carros> listadoCarros, int resource){
        this.listadoCarros=listadoCarros;
        this.resource=resource;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Carros carro=listadoCarros.get(position);
        holder.txt_marca.setText(carro.getMarca());
        holder.txt_modelo.setText(carro.getModelo());
        holder.txt_placa.setText(carro.getPlaca());
        holder.txt_color.setText(carro.getColor());
    }

    @Override
    public int getItemCount() {
        return listadoCarros.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ArrayList<Carros> ListadoCarros;
        private TextView txt_marca;
        private TextView txt_modelo;
        private TextView txt_placa;
        private TextView txt_color;
        public View view;
        public ViewHolder(View view){
            super(view);
            this.view=view;
            this.txt_marca=(TextView) view.findViewById(R.id.tv_marca_vehiculo);
            this.txt_modelo=(TextView) view.findViewById(R.id.tv_modelo_vehiculo);
            this.txt_color=(TextView) view.findViewById(R.id.tv_color_vehiculo);
            this.txt_placa=(TextView) view.findViewById(R.id.tv_placa_vehiculo);
        }
    }
}
