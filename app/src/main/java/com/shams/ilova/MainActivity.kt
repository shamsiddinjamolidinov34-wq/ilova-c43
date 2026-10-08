@Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
getWindow().getDecorView().setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_FULLSCREEN
);

FrameLayout layout = new FrameLayout(this);

ImageView imageView = new ImageView(this);
imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
imageView.setBackgroundColor(0xFF000000);

layout.addView(imageView, new FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT,
        Gravity.CENTER
));

setContentView(layout);

new Thread(() -> {
    Bitmap bitmap = null;
    HttpURLConnection connection = null;

    try {
        URL url = new URL(
            "https://i.postimg.cc/mD8N0Q2B/gorilla.jpg"
        );

        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(15000);
        connection.setRequestMethod("GET");
        connection.connect();

        try (InputStream is = connection.getInputStream()) {
            bitmap = BitmapFactory.decodeStream(is);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (connection != null) {
            connection.disconnect();
        }
    }

    final Bitmap result = bitmap;

    runOnUiThread(() -> {
        if (result != null) {
            imageView.setImageBitmap(result);
        }
    });
}).start();
}
