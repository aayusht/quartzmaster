package com.aayush.quartzmaster;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aayush on 10/5/17.
 */

public class TileAdapter extends RecyclerView.Adapter<TileAdapter.CustomViewHolder> {
    Context context;
    ArrayList<Tile> tileArrayList = new ArrayList<>();
    boolean isFavorites;

    public TileAdapter(Context context, boolean isFavorites) {
        this.context = context;
        this.isFavorites = isFavorites;
        if (!isFavorites) load();
        else loadFavorites();
    }

    public void search(String s) {
        tileArrayList.clear();
        for (Tile tile : tiles) {
            boolean scenery = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.scenery, true);
            boolean marble = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.marble, true);
            boolean mineral = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.mineral, true)
                    && context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.mc_checkbox, true);;
            boolean starlight = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.starlight, true);
            boolean sound = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.sound, true);
            boolean granite = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.granite, true);
            boolean anything = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.thickness_checkbox, true)
                    && context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.finish_checkbox, true);
            boolean nonmineral = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.qm_checkbox, true);
            if(tile.getID().startsWith(s) || tile.getName().substring(tile.getName().indexOf(' ') + 1).toLowerCase().startsWith(s)) {
                if (anything) {
                    if (nonmineral) {
                        if (marble && tile.collection.equals("The Marble Collection"))
                            tileArrayList.add(tile);
                        if (starlight && tile.collection.equals("Scenery Series"))
                            tileArrayList.add(tile);
                        if (scenery && tile.collection.equals("Scenery Series"))
                            tileArrayList.add(tile);
                        if (sound && tile.collection.equals("Sound Series"))
                            tileArrayList.add(tile);
                        if (granite && tile.collection.equals("GRANITE LOOK"))
                            tileArrayList.add(tile);
                    }
                    if (mineral && tile.collection.equals("The Mineral Collection"))
                        tileArrayList.add(tile);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void load() {
        Log.d("I am here now", "here");
        tileArrayList.clear();
        for (Tile tile : tiles) {
            boolean scenery = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.scenery, true);
            boolean marble = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.marble, true);
            boolean mineral = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.mineral, true)
                    && context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.mc_checkbox, true);;
            boolean starlight = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.starlight, true);
            boolean sound = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.sound, true);
            boolean granite = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.granite, true);
            boolean anything = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.thickness_checkbox, true)
                    && context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.finish_checkbox, true);
            boolean nonmineral = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE).getBoolean("" + R.id.qm_checkbox, true);
            if (anything) {
                if (nonmineral) {
                    if (marble && tile.collection.equals("The Marble Collection"))
                        tileArrayList.add(tile);
                    if (starlight && tile.collection.equals("Scenery Series"))
                        tileArrayList.add(tile);
                    if (scenery && tile.collection.equals("Scenery Series")) tileArrayList.add(tile);
                    if (sound && tile.collection.equals("Sound Series")) tileArrayList.add(tile);
                    if (granite && tile.collection.equals("GRANITE LOOK")) tileArrayList.add(tile);
                }
                if (mineral && tile.collection.equals("The Mineral Collection")) tileArrayList.add(tile);
            }
        }
        Log.d("rip", "" + this.tileArrayList.size());
    }

    private void loadFavorites() {
        tileArrayList.clear();
        if (isFavorites) {
            for (Tile tile2 : tiles) {
                if (context.getSharedPreferences(context.getString(R.string.preferences_key),
                        Context.MODE_PRIVATE).getInt(tile2.getID(), 0) == 1) {
                    tileArrayList.add(tile2);
                }

            }
        }
    }

    public void loadTiles() {
        if (!isFavorites) load();
        else loadFavorites();
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tile_view, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Tile tile = tileArrayList.get(position);
        holder.title.setText(tile.name);
        holder.image.setImageResource(tile.drawable1);
        String type = "";
        switch(tile.collection) {
            case "The Marble Collection":
                type = "MARBLE";
                break;
            case "Scenery Series":
                type = "SCENERY SERIES";
                break;
            case "Starlight Series":
                type = "STARLIGHT SERIES";
                break;
            case "Sound Series":
                type = "SOUND SERIES";
                break;
            case "GRANITE LOOK":
                type = "GRANITE";
                break;
            case "The Mineral Collection":
                type = "MINERAL";
                break;
        }
        holder.type.setText(type);
        if (!this.isFavorites) {
            holder.container.findViewById(R.id.star).setVisibility(View.GONE);
            holder.container.findViewById(R.id.staroutline).setVisibility(View.GONE);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context == null) Log.e("What the heck", "context is null");
                else {
                    Intent intent = new Intent(context, TileActivity.class);
                    intent.putExtra("tile", tile);
                    context.startActivity(new Intent(intent));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tileArrayList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        TextView type;
        View container;

        CustomViewHolder (View view) {
            super(view);
            title = view.findViewById(R.id.tileName);
            image = view.findViewById(R.id.tileImage);
            type = view.findViewById(R.id.tileType);
            container = view.findViewById(R.id.tile);
        }
    }

    static final Tile[] tiles = {
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9606-calacutta-michael-angelo/", "The Marble Collection", "QM9606 Calacutta Michael Angelo", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm96061, R.drawable.qm96062, R.drawable.qm96063),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9607-statuario-white/", "The Marble Collection", "QM9607 Statuario White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm96071, R.drawable.qm96072, R.drawable.qm96073),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9722-vanilla-white/", "The Marble Collection", "QM9722 Vanilla White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97221, R.drawable.qm97222, R.drawable.qm97223),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9716-sugar-white/", "The Marble Collection", "QM9716 Sugar White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97161, R.drawable.qm97162, R.drawable.qm97163),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9723-cobra-black/", "The Marble Collection", "QM9723 Cobra Black", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97231, R.drawable.qm97232, R.drawable.qm97233),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9724-sahara-beige/", "The Marble Collection", "QM9724 Sahara Beige", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97241, R.drawable.qm97242, R.drawable.qm97243),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9717-calacutta-capraia/", "The Marble Collection", "QM9717 Calacutta Capraia", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97171, R.drawable.qm97172, R.drawable.qm97173),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9715-vancouver-black/", "The Marble Collection", "QM9715 Vancouver Black", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97151, R.drawable.qm97152, R.drawable.qm97153),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9296-lincoln-gold/", "The Marble Collection", "QM9296 Lincoln Gold", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92961, R.drawable.qm92962, R.drawable.qm92963),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9708-calacutta-italia-2/", "The Marble Collection", "QM9708 Calacutta Italia", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97081, R.drawable.qm97082, R.drawable.qm97083),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9714-costello/", "The Marble Collection", "QM9714 Costello", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97141, R.drawable.qm97142, R.drawable.qm97143),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9292-statuario-classic/", "The Marble Collection", "QM9292 Statuario Classic", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92921, R.drawable.qm92922, 0),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9281-calacutta-borghini/", "The Marble Collection", "QM9281 Calacutta Borghini", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92811, R.drawable.qm92812, R.drawable.qm92813),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm8003-latte-2/", "The Marble Collection", "QM8003 Latte", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm80031, R.drawable.qm80032, R.drawable.qm80033),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm8008-cararra/", "The Marble Collection", "QM8008 Cararra", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm80081, R.drawable.qm80082, R.drawable.qm80083),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm-9287-palazzo-dark/", "The Marble Collection", "QM9287 Palazzo Dark", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92871, R.drawable.qm92872, R.drawable.qm92873),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9285-cream-marfil/", "The Marble Collection", "QM9285 Cream Marfil", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92851, R.drawable.qm92852, R.drawable.qm92853),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9284-empire/", "The Marble Collection", "QM9284 Empire", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92841, R.drawable.qm92842, R.drawable.qm92843),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9600-nero-marquina/", "The Marble Collection", "QM9600 Nero Marquina", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm96001, R.drawable.qm96002, R.drawable.qm96003),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9604-white-aran/", "The Marble Collection", "QM9604 White Aran", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm96041, R.drawable.qm96042, R.drawable.qm96043),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9709-south-beach-white/", "The Marble Collection", "QM9709 South Beach White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97091, R.drawable.qm97092, R.drawable.qm97093),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9012-bottichino-classic/", "The Marble Collection", "QM9012 Bottichino Classic", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm90121, R.drawable.qm90122, R.drawable.qm90123),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9110-calacutta-royal/", "The Marble Collection", "QM9110 Calacutta Royal", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm91101, R.drawable.qm91102, R.drawable.qm91103),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9112-calacutta-vagli/", "The Marble Collection", "QM9112 Calacutta Vagli", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm91121, R.drawable.qm91122, R.drawable.qm91123),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9131-mother-pearl/", "The Marble Collection", "QM9131 Mother Pearl", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm91311, R.drawable.qm91312, R.drawable.qm91313),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9132-palazzo-light/", "The Marble Collection", "QM9132 Palazzo Light", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm91321, R.drawable.qm91322, R.drawable.qm91323),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9280-calacutta-bnc/", "The Marble Collection", "QM9280 Calacutta Bnc", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92801, R.drawable.qm92802, R.drawable.qm92803),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9282-calacutta-gold/", "The Marble Collection", "QM9282 Calacutta Gold", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92821, R.drawable.qm92822, R.drawable.qm92823),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9283-calacutta-grey/", "The Marble Collection", "QM9283 Calacutta Grey", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92831, R.drawable.qm92832, R.drawable.qm92833),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9286-organic-white/", "The Marble Collection", "QM9286 Organic White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92861, R.drawable.qm92862, R.drawable.qm92863),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9288-jerusalem-gold/", "The Marble Collection", "QM9288 Jerusalem Gold", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92881, R.drawable.qm92882, R.drawable.qm92883),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9290-statuario-venato/", "The Marble Collection", "QM9290 Statuario Venato", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92901, R.drawable.qm92902, R.drawable.qm92903),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9294-calacutta-lincoln/", "The Marble Collection", "QM9294 Calacutta Lincoln", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92941, R.drawable.qm92942, R.drawable.qm92943),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9295-calacutta-miel/", "The Marble Collection", "QM9295 Calacutta Miel", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92951, R.drawable.qm92952, R.drawable.qm92953),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9706-white-macaubas/", "The Marble Collection", "QM9706 White Macaubas", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97061, R.drawable.qm97062, R.drawable.qm97063),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9601-calacutta-manhattan/", "The Marble Collection", "QM9601 Calacutta Manhattan", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm96011, R.drawable.qm96012, R.drawable.qm96013),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9705-calacutta-luna/", "The Marble Collection", "QM9705 Calacutta Luna", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97051, R.drawable.qm97052, R.drawable.qm97053),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9707-calacutta-lellair/", "The Marble Collection", "QM9707 Calacutta L’ellair", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97071, R.drawable.qm97072, R.drawable.qm97073),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm6800-white-arabascato/", "The Marble Collection", "QM6800 White Arabascato", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm68001, R.drawable.qm68002, R.drawable.qm68003),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9293-alaskan-white/", "The Marble Collection", "QM9293 Alaskan White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm92931, R.drawable.qm92932, R.drawable.qm92933),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9605-venato-gold/", "The Marble Collection", "QM9605 Venato Gold", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm96051, R.drawable.qm96052, R.drawable.qm96053),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3004-silver-star-black/", "Starlight Series", "QM3004 Silver Star Black", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30041, R.drawable.qm30042, R.drawable.qm30043),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3005-silver-star-red/", "Starlight Series", "QM3005 Silver Star Red", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30051, R.drawable.qm30052, R.drawable.qm30053),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3007-universe-blue/", "Starlight Series", "QM3007 Universe Blue", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30071, R.drawable.qm30072, R.drawable.qm30073),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3009-silver-star-grey/", "Starlight Series", "QM3009 Silver Star Grey", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30091, R.drawable.qm30092, R.drawable.qm30093),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3011-irish-diamond-yellow/", "Starlight Series", "QM3011 Irish Diamond Yellow", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30111, R.drawable.qm30112, R.drawable.qm30113),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3012-irish-diamond-white/", "Starlight Series", "QM3012 Irish Diamond White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30121, R.drawable.qm30122, R.drawable.qm30123),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3014-galaxies-special-order-only/", "Starlight Series", "QM3014 Galaxies Special Order Only", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30141, R.drawable.qm30142, R.drawable.qm30143),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3015-galaxy-white/", "Starlight Series", "QM3015 Galaxy White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30151, R.drawable.qm30152, R.drawable.qm30153),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9001-titanium-black/", "Starlight Series", "QM9001 Titanium Black", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm90011, R.drawable.qm90012, 0),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9003-titanium-silver/", "Starlight Series", "QM9003 Titanium Silver", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm90031, R.drawable.qm90032, 0),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm3001-silver-star-white/", "Starlight Series", "QM3001 Silver Star White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm30011, R.drawable.qm30012, R.drawable.qm30013),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm2011-liliaceous-land/", "Scenery Series", "QM2011 Liliaceous Land", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm20111, R.drawable.qm20112, R.drawable.qm20113),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm5006-sand-brown/", "Scenery Series", "QM5006 Sand Brown", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm50061, R.drawable.qm50062, R.drawable.qm50063),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm5007-indian-red/", "Scenery Series", "QM5007 Indian Red", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm50071, R.drawable.qm50072, R.drawable.qm50073),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm5010-congo-black/", "Scenery Series", "QM5010 Congo Black", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm50101, R.drawable.qm50102, R.drawable.qm50103),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm5022-maori-island/", "Scenery Series", "QM5022 Maori Island", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm50221, R.drawable.qm50222, R.drawable.qm50223),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm4005-blue-seasky/", "Scenery Series", "QM4005 Blue Sea&Sky", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm40051, R.drawable.qm40052, R.drawable.qm40053),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm1001-beach-white/", "Sound Series", "QM1001 Beach White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm10011, R.drawable.qm10012, R.drawable.qm10013),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm1005-crystal-grey/", "Sound Series", "QM1005 Crystal Grey", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm10051, R.drawable.qm10052, R.drawable.qm10053),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm1011-bianco-gentle/", "Sound Series", "QM1011 Bianco Gentle", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm10111, R.drawable.qm10112, R.drawable.qm10113),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm1014-soho-grey/", "Sound Series", "QM1014 Soho Grey", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm10141, R.drawable.qm10142, R.drawable.qm10143),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm2012-jasmine/", "Sound Series", "QM2012 Jasmine", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm20121, R.drawable.qm20122, R.drawable.qm20123),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm4000-ancient-path/", "Sound Series", "QM4000 Ancient Path", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm40001, R.drawable.qm40002, 0),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm8009-alpine-grey/", "Sound Series", "QM8009 Alpine Grey", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm80091, R.drawable.qm80092, R.drawable.qm80093),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm8011-explosion-white/", "Sound Series", "QM8011 Explosion White", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm80111, R.drawable.qm80112, R.drawable.qm80113),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm8120-armari-grey/", "Sound Series", "QM8120 Armari Grey", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm81201, R.drawable.qm81202, R.drawable.qm81203),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm1003-beach-grey/", "Sound Series", "QM1003 Beach Grey", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm10031, R.drawable.qm10032, R.drawable.qm10033),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9009-cecila/", "GRANITE LOOK", "QM9009 CECILA", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm90091, R.drawable.qm90092, R.drawable.qm90093),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9010-pearl-blue/", "GRANITE LOOK", "QM9010 PEARL BLUE", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm90101, R.drawable.qm90102, R.drawable.qm90103),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9703-blanco-romano/", "GRANITE LOOK", "QM9703 BLANCO ROMANO", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97031, R.drawable.qm97032, 0),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9700-white-delicatus/", "GRANITE LOOK", "QM9700 WHITE DELICATUS", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97001, R.drawable.qm97002, R.drawable.qm97003),
            new Tile ("http://www.quartzmasters.com/portfolio-item/qm9702-metallic/", "GRANITE LOOK", "QM9702 METALLIC", "120″ x 64″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.qm97021, R.drawable.qm97022, R.drawable.qm97023),
            new Tile ("http://www.quartzmasters.com/portfolio-item/mc0700-tiger-eye/", "The Mineral Collection", "Mc0700 Tiger Eye", "120″ x  55″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.mc07001, R.drawable.mc07002, R.drawable.mc07003),
            new Tile ("http://www.quartzmasters.com/portfolio-item/mc0600-grey-agate/", "The Mineral Collection", "Mc0600 Grey Agate", "120″ x 55″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.mc06001, R.drawable.mc06002, R.drawable.mc06003),
            new Tile ("http://www.quartzmasters.com/portfolio-item/mc0400-brown-agate/", "The Mineral Collection", "Mc0400 Brown Agate", "120″ x 55″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.mc04001, R.drawable.mc04002, R.drawable.mc04003),
            new Tile ("http://www.quartzmasters.com/portfolio-item/mc0300-blue-agate-classic/", "The Mineral Collection", "Mc0300 Blue Agate Classic", "120″ x 55″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.mc03001, R.drawable.mc03002, R.drawable.mc03003),
            new Tile ("http://www.quartzmasters.com/portfolio-item/mc0200-black-agate-wild/", "The Mineral Collection", "Mc0200 Black Agate Wild", "120″ x 55 (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.mc02001, R.drawable.mc02002, R.drawable.mc02003),
            new Tile ("http://www.quartzmasters.com/portfolio-item/mc0100-amethyst/", "The Mineral Collection", "Mc0100 Amethyst", "120″ x 55″ (all slab sizes)", "1-1/4″ or 3/4″", R.drawable.mc01001, R.drawable.mc01002, R.drawable.mc01003)
    };
}
