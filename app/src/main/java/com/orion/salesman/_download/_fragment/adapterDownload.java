package com.orion.salesman._download._fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.IF_122;
import com.orion.salesman._interface.IF_10;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.IF_103;
import com.orion.salesman._interface.IF_104;
import com.orion.salesman._interface.IF_110;
import com.orion.salesman._interface.IF_121;
import com.orion.salesman._interface.IF_123;
import com.orion.salesman._interface.IF_129;
import com.orion.salesman._interface.IF_130;
import com.orion.salesman._interface.IF_131;
import com.orion.salesman._object.CheckUpdate;
import com.orion.salesman._object.DownList;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._object.IF_128;
import com.orion.salesman._route._object.ObjectA0128;
import com.orion.salesman._summary._adapter.SalesReportAdapterChild;
import com.orion.salesman._summary._object.SalesReport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Huy on 8/9/2016.
 */
public class adapterDownload extends RecyclerView.Adapter<adapterDownload.MyViewHolder> {
    private String TAG = "adapterDownload";
    private List<ObjectDownload> moviesList;
    private Context context;
    private DownloadFragment instance;

    public List<ObjectDownload> getList() {
        return moviesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour;
        public CheckBox checkBox, cbTitle;
        public FrameLayout frThree;
        public ImageView IMG_CHECK;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            checkBox = (CheckBox) view.findViewById(R.id.cb);
            cbTitle = (CheckBox) view.findViewById(R.id.cbTitle);
            frThree = (FrameLayout) view.findViewById(R.id.frThree);
            IMG_CHECK = (ImageView) view.findViewById(R.id.IMG_CHECK);
        }
    }

    public void updateCheck_UnCheck(boolean c) {
        for (int i = 0; i < moviesList.size(); i++) {
            moviesList.get(i).setCheck(c);
        }
        this.notifyDataSetChanged();
    }

    public adapterDownload(Context context, List<ObjectDownload> moviesList, DownloadFragment instance) {
        this.context = context;
        this.moviesList = moviesList;
        this.instance = instance;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_download, parent, false);

        return new MyViewHolder(itemView);
    }

    void setbackground(View v) {
//        v.setBackgroundColor(Color.parseColor("#dce6f2"));
        v.setBackgroundColor(Color.parseColor("#e6b9b8"));
    }

    public void REMOVE_ALL_CHECK_DOWNLOAD() {
        for (int i = 0; i < moviesList.size(); i++) {
            moviesList.get(i).setIsDownload(false);
        }
        notifyDataSetChanged();
    }

    public void updateDownLoadSuccess(int P) {
        moviesList.get(P).setIsDownload(true);
        notifyItemChanged(P);
    }

    void roundBackground(View v) {
        v.setBackgroundResource(R.drawable.round_button);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ObjectDownload movie = moviesList.get(position);
        holder.IMG_CHECK.setVisibility(movie.isDownload() ? View.VISIBLE : View.GONE);
        holder.tvThree.setVisibility(View.VISIBLE);
        holder.tvFour.setVisibility(View.GONE);
        Const.setColor_0(holder.tvTwo);
        Const.setColor_0(holder.frThree);
        Const.setTextBlack(holder.tvOne);
        Const.setTextBlack(holder.tvTwo);
        setbackground(holder.tvOne);
        roundBackground(holder.tvThree);
        holder.cbTitle.setVisibility(View.GONE);
        holder.checkBox.setVisibility(View.VISIBLE);
        holder.checkBox.setChecked(movie.isCheck());
        holder.tvTwo.setText(movie.getV2());
        holder.tvOne.setText(movie.getV1());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.checkBox.isChecked()) {
                    moviesList.get(position).setCheck(holder.checkBox.isChecked());
                    instance.setCheckCB(holder.checkBox.isChecked());
                } else {
                    moviesList.get(position).setCheck(holder.checkBox.isChecked());
                    if (checkList(moviesList))
                        instance.setCheckCB(true);
                }
            }
        });
        holder.tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DownloadFragment.instance.selectDownLoad();

                download(position);
            }
        });
    }

    void finishDownload(int position) {
        moviesList.get(position).setIsDownload(true);
        notifyItemChanged(position);
    }

    void download(final int position) {
        String YESTERDAY = "";
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        //Sunday = 1, Monday = 2,...

        if (dayOfWeek == 2) {
            YESTERDAY = Const.getToday(-2);
        } else {
            YESTERDAY = Const.getToday(-1);
        }
        moviesList.get(position).setIsDownload(false);
        notifyItemChanged(position);

        MainActivity.instance.showFR();

        if (position == 0) {
            int cur = 0;
            MainActivity.instance.setStatusDownload("Download Product & Display Data", Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_10(MainActivity.dataLogin, 0, new IF_10() {
                @Override
                public void onSuccess(String s) {
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload("Download Product & Display Data", Const.setStatusProgressbar(cur, 1), cur, 1);

                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "Download Address & Common Data FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 1) {
            int cur = 0;
            MainActivity.instance.setStatusDownload("Download Product & Display Data", Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_10(MainActivity.dataLogin, 1, new IF_10() {
                @Override
                public void onSuccess(String s) {
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload("Download Product & Display Data", Const.setStatusProgressbar(cur, 1), cur, 1);

                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "Download Product & Display Data FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 2) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_100), Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_100(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_100() {
                @Override
                public void onSuccess() {
                    new PrefManager(context).setSummary_1(true);
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_100), Const.setStatusProgressbar(cur, 1), cur, 1);

                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 100 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 3) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_101), Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_130_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_130() {
                @Override
                public void onSuccess() {
                    new PrefManager(context).setSummary_2(true);
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_101), Const.setStatusProgressbar(cur, 1), cur, 1);
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 130 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 4) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_104), Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_104_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_104() {
                @Override
                public void onSuccess() {
                    new PrefManager(context).setSummary_3(true);
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_104), Const.setStatusProgressbar(cur, 1), cur, 1);
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 104 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 5) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_103), Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_103_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_103() {
                @Override
                public void onSuccess() {
                    new PrefManager(context).setSummary_4(true);
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_103), Const.setStatusProgressbar(cur, 1), cur, 1);
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 103 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 6) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_110) + " " + Const.getToday(), Const.setStatusProgressbar(cur, 2), cur, 2);
            new HttpRequest(context).new API_110_(MainActivity.dataLogin, RouteFragment.KEY_ROUTE, new IF_110() {
                @Override
                public void onSuccess() {
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_110) + " " + Const.getToday(), Const.setStatusProgressbar(cur, 2), cur, 2);

                    int RouteNo = 0;
                    try {
                        RouteNo = Integer.parseInt(MainActivity.dataLogin.getROUTENO());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String KEY_ROUTE = "1";
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -1);
                    int pos = cal.get(Calendar.DAY_OF_WEEK);// sunday = 1, sat = 7
                    pos -= 1;
                    if (pos <= 0)
                        pos = 1;

                    if (RouteNo == 0) {
                        if (pos == 4) KEY_ROUTE = "1";
                        else if (pos == 5) KEY_ROUTE = "2";
                        else if (pos == 6) KEY_ROUTE = "3";
                        else KEY_ROUTE = "" + pos;
                    } else {
                        KEY_ROUTE = "" + pos;
                    }
                    new HttpRequest(context).new API_110_(MainActivity.dataLogin, KEY_ROUTE, new IF_110() {
                        @Override
                        public void onSuccess() {
                            Calendar cal = Calendar.getInstance();
                            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                            //Sunday = 1, Monday = 2,...
                            String s = "";
                            if (dayOfWeek == 2) {
                                s = Const.getToday(-2);
                            } else {
                                s = Const.getToday(-1);
                            }
                            int cur = 2;
                            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_110) + " " + s, Const.setStatusProgressbar(cur, 2), cur, 2);

                            finishDownload(position);
                            MainActivity.instance.hideFR();

                            new PrefManager(context).setUpdateRoute(true);
                        }

                        @Override
                        public void onFail() {
                            MainActivity.instance.hideFR();
                            Toast.makeText(context, "API 110 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                        }
                    }, 0).execute();
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 110 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }, 1).execute();
        } else if (position == 7) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_120), Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_120_(MainActivity.dataLogin, new IF_100() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_120), Const.setStatusProgressbar(cur, 1), cur, 1);
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 120 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 8) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_122), Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_122_(MainActivity.dataLogin, new IF_122() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_122), Const.setStatusProgressbar(cur, 1), cur, 1);
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 122 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 9) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_123), Const.setStatusProgressbar(cur, 1), cur, 1);
            new HttpRequest(context).new API_123_(MainActivity.dataLogin, new IF_123() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    MainActivity.instance.hideFR();
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_123), Const.setStatusProgressbar(cur, 1), cur, 1);
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 123 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 10) {
            int cur = 0;
            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_121) + " " + Const.getToday(), Const.setStatusProgressbar(cur, 2), cur, 2);
            final String preMonth = Const.getPreMonth() + "15";
            new HttpRequest(context).new API_121_(MainActivity.dataLogin, new IF_121() {
                @Override
                public void onSuccess() {
                    int cur = 1;
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_121) + " " + Const.getToday(), Const.setStatusProgressbar(cur, 2), cur, 2);
                    new HttpRequest(context).new API_121_(MainActivity.dataLogin, new IF_121() {
                        @Override
                        public void onSuccess() {
                            int cur = 2;
                            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_121) + " " + Const.getPreMonth(), Const.setStatusProgressbar(cur, 2), cur, 2);
                            finishDownload(position);
                            MainActivity.instance.hideFR();
                            Toast.makeText(context, context.getResources().getString(R.string.API_121) + " OK", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail() {
                            MainActivity.instance.hideFR();
                            Toast.makeText(context, "API 121 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                        }
                    }, preMonth).execute();
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 121 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }, Const.getToday()).execute();
        } else if (position == 11) {
            int cur = 0;
            MainActivity.instance.setStatusDownload("Order OMI Info " + Const.getToday(), Const.setStatusProgressbar(cur, 2), cur, 2);
            final String finalYESTERDAY = YESTERDAY;
            new HttpRequest(context).new A0128(MainActivity.dataLogin, TODAY, new IF_128() {
                @Override
                public void onSuccess(List<ObjectA0128> list) {
                    int cur = 1;
                    MainActivity.instance.setStatusDownload("Order OMI Info " + Const.getToday(), Const.setStatusProgressbar(cur, 2), cur, 2);
                    new HttpRequest(context).new A0128(MainActivity.dataLogin, finalYESTERDAY, new IF_128() {
                        @Override
                        public void onSuccess(List<ObjectA0128> list) {
                            int cur = 2;
                            MainActivity.instance.setStatusDownload("Order OMI Info " + finalYESTERDAY, Const.setStatusProgressbar(cur, 2), cur, 2);
                            finishDownload(position);
                            MainActivity.instance.hideFR();
                        }

                        @Override
                        public void onFail() {
                            MainActivity.instance.hideFR();
                            Toast.makeText(context, "Order OMI Info FAIL", Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "Order OMI Info FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 12) {
            int cur = 0;
            MainActivity.instance.setStatusDownload("Order OMI Detail Info " + Const.getToday(), Const.setStatusProgressbar(cur, 2), cur, 2);
            final String finalYESTERDAY1 = YESTERDAY;
            new HttpRequest(context).new A0129(MainActivity.dataLogin, new IF_129() {
                @Override
                public void onSuccess() {
                    int cur = 1;
                    MainActivity.instance.setStatusDownload("Order OMI Detail Info " + Const.getToday(), Const.setStatusProgressbar(cur, 2), cur, 2);
                    new HttpRequest(context).new A0129(MainActivity.dataLogin, new IF_129() {
                        @Override
                        public void onSuccess() {

                            int cur = 2;
                            MainActivity.instance.setStatusDownload("Order OMI Detail Info " + finalYESTERDAY1, Const.setStatusProgressbar(cur, 2), cur, 2);
                            finishDownload(position);
                            MainActivity.instance.hideFR();

                        }

                        @Override
                        public void onFail() {
                            MainActivity.instance.hideFR();
                            Toast.makeText(context, "Order OMI Detail Info FAIL", Toast.LENGTH_SHORT).show();
                        }
                    }, finalYESTERDAY1).execute();

                }

                @Override
                public void onFail() {
                    MainActivity.instance.hideFR();
                    Toast.makeText(context, "Order OMI Detail Info FAIL", Toast.LENGTH_SHORT).show();
                }
            }, TODAY).execute();
        }
    }

    boolean checkList(List<ObjectDownload> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (!arr.get(i).isCheck())
                return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void selectDownload(final int position, final int sum, final int SumCount, final int cur) {
        String YESTERDAY = "";
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        final String TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        //Sunday = 1, Monday = 2,...

        if (dayOfWeek == 2) {
            YESTERDAY = Const.getToday(-2);
        } else {
            YESTERDAY = Const.getToday(-1);
        }
        moviesList.get(position).setIsDownload(false);
        notifyItemChanged(position);

        if (position == 0) {
            new HttpRequest(context).new API_10(MainActivity.dataLogin, 0, new IF_10() {
                @Override
                public void onSuccess(String s) {
                    MainActivity.instance.setStatusDownload("Download Address & Common Data", Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                    finishDownload(position);

                    if (sum == 1)
                        MainActivity.instance.hideFR();
                }

                @Override
                public void onFail() {
                    Toast.makeText(context, "Download Address & Common Data FAIL", Toast.LENGTH_SHORT).show();
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                }
            }).execute();
        } else if (position == 1) {
            new HttpRequest(context).new API_10(MainActivity.dataLogin, 1, new IF_10() {
                @Override
                public void onSuccess(String s) {
                    finishDownload(position);
                    MainActivity.instance.setStatusDownload("Download Product & Display Data", Const.setStatusProgressbar(cur, SumCount), cur, SumCount);

                    if (sum == 1)
                        MainActivity.instance.hideFR();
                }

                @Override
                public void onFail() {
                    Toast.makeText(context, "Download Product & Display Data FAIL", Toast.LENGTH_SHORT).show();
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                }
            }).execute();
        } else if (position == 2) {
            new HttpRequest(context).new API_100(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_100() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_100), Const.setStatusProgressbar(cur, SumCount), cur, SumCount);

                    if (sum == 1)
                        MainActivity.instance.hideFR();
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 100 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 3) {
            new HttpRequest(context).new API_130_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_130() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_101), Const.setStatusProgressbar(cur, SumCount), cur, SumCount);

                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 130 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 4) {
            new HttpRequest(context).new API_104_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_104() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    if (sum == 1)
                        MainActivity.instance.hideFR();

                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_104), Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 104 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 5) {
            new HttpRequest(context).new API_103_(MainActivity.DATE_SYNC, MainActivity.dataLogin, new IF_103() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    if (sum == 1)
                        MainActivity.instance.hideFR();

                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_103), Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 103 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 6) {
            new HttpRequest(context).new API_110_(MainActivity.dataLogin, RouteFragment.KEY_ROUTE, new IF_110() {
                @Override
                public void onSuccess() {
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_110) + " " + Const.getToday(), Const.setStatusProgressbar(cur-1, SumCount), cur-1, SumCount);
                    int RouteNo = 0;
                    try {
                        RouteNo = Integer.parseInt(MainActivity.dataLogin.getROUTENO());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String KEY_ROUTE = "1";
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -1);
                    int pos = cal.get(Calendar.DAY_OF_WEEK);// sunday = 1, sat = 7
                    pos -= 1;
                    if (pos <= 0)
                        pos = 1;

                    if (RouteNo == 0) {
                        if (pos == 4) KEY_ROUTE = "1";
                        else if (pos == 5) KEY_ROUTE = "2";
                        else if (pos == 6) KEY_ROUTE = "3";
                        else KEY_ROUTE = "" + pos;
                    } else {
                        KEY_ROUTE = "" + pos;
                    }

                    new HttpRequest(context).new API_110_(MainActivity.dataLogin, KEY_ROUTE, new IF_110() {
                        @Override
                        public void onSuccess() {
                            new PrefManager(context).setUpdateRoute(true);
                            finishDownload(position);
                            if (sum == 1)
                                MainActivity.instance.hideFR();
                            Calendar cal = Calendar.getInstance();
                            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                            //Sunday = 1, Monday = 2,...
                            String s = "";
                            if (dayOfWeek == 2) {
                                s = Const.getToday(-2);
                            } else {
                                s = Const.getToday(-1);
                            }
                            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_110) + " " + s, Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                        }

                        @Override
                        public void onFail() {
                            if (sum == 1)
                                MainActivity.instance.hideFR();
                            Toast.makeText(context, "API 110 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                        }
                    }, 0).execute();
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 110 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }, 1).execute();
        } else if (position == 7) {
            new HttpRequest(context).new API_120_(MainActivity.dataLogin, new IF_100() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    if (sum == 1)
                        MainActivity.instance.hideFR();

                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_120), Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 120 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 8) {
            new HttpRequest(context).new API_122_(MainActivity.dataLogin, new IF_122() {
                @Override
                public void onSuccess() {
                    finishDownload(position);
                    if (sum == 1)
                        MainActivity.instance.hideFR();

                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_122), Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 122 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 9) {
            new HttpRequest(context).new API_123_(MainActivity.dataLogin, new IF_123() {
                @Override
                public void onSuccess() {
                    finishDownload(position);

                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_123), Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 123 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 10) {
            final String preMonth = Const.getPreMonth() + "15";
            new HttpRequest(context).new API_121_(MainActivity.dataLogin, new IF_121() {
                @Override
                public void onSuccess() {
                    MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_121) + " " + Const.getToday(), Const.setStatusProgressbar(cur-1, SumCount), cur-1, SumCount);
                    new HttpRequest(context).new API_121_(MainActivity.dataLogin, new IF_121() {
                        @Override
                        public void onSuccess() {
                            finishDownload(position);
                            if (sum == 1)
                                MainActivity.instance.hideFR();
                            Calendar cal = Calendar.getInstance();
                            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                            //Sunday = 1, Monday = 2,...
                            String s = "";
                            if (dayOfWeek == 2) {
                                s = Const.getToday(-2);
                            } else {
                                s = Const.getToday(-1);
                            }

                            MainActivity.instance.setStatusDownload(context.getResources().getString(R.string.API_121) + " " + s, Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                        }

                        @Override
                        public void onFail() {
                            if (sum == 1)
                                MainActivity.instance.hideFR();
                            Toast.makeText(context, "API 121 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                        }
                    }, preMonth).execute();
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "API 121 FAIL " + context.getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                }
            }, Const.getToday()).execute();
        } else if (position == 11) {
            final String finalYESTERDAY = YESTERDAY;
            new HttpRequest(context).new A0128(MainActivity.dataLogin, TODAY, new IF_128() {
                @Override
                public void onSuccess(List<ObjectA0128> list) {
                    MainActivity.instance.setStatusDownload("Order OMI Info " + TODAY, Const.setStatusProgressbar(cur-1, SumCount), cur-1, SumCount);
                    new HttpRequest(context).new A0128(MainActivity.dataLogin, finalYESTERDAY, new IF_128() {
                        @Override
                        public void onSuccess(List<ObjectA0128> list) {
                            finishDownload(position);
                            if (sum == 1)
                                MainActivity.instance.hideFR();
                            MainActivity.instance.setStatusDownload("Order OMI Info " + finalYESTERDAY, Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                        }

                        @Override
                        public void onFail() {
                            if (sum == 1)
                                MainActivity.instance.hideFR();
                            Toast.makeText(context, "Order OMI Info FAIL", Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "Order OMI Info FAIL", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else if (position == 12) {
            final String finalYESTERDAY1 = YESTERDAY;
            new HttpRequest(context).new A0129(MainActivity.dataLogin, new IF_129() {
                @Override
                public void onSuccess() {
                    MainActivity.instance.setStatusDownload("Order OMI Detail Info " + TODAY, Const.setStatusProgressbar(cur-1, SumCount), cur-1, SumCount);
                    new HttpRequest(context).new A0129(MainActivity.dataLogin, new IF_129() {
                        @Override
                        public void onSuccess() {
                            MainActivity.instance.setStatusDownload("Order OMI Detail Info " + finalYESTERDAY1, Const.setStatusProgressbar(cur, SumCount), cur, SumCount);
                            finishDownload(position);

                            if (sum == 1)
                                MainActivity.instance.hideFR();
                        }

                        @Override
                        public void onFail() {
                            if (sum == 1)
                                MainActivity.instance.hideFR();
                            Toast.makeText(context, "Order OMI Detail Info FAIL", Toast.LENGTH_SHORT).show();
                        }
                    }, finalYESTERDAY1).execute();

                }

                @Override
                public void onFail() {
                    if (sum == 1)
                        MainActivity.instance.hideFR();
                    Toast.makeText(context, "Order OMI Detail Info FAIL", Toast.LENGTH_SHORT).show();
                }
            }, TODAY).execute();
        }
    }
}
