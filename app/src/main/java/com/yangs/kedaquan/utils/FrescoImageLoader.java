package com.yangs.kedaquan.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;
import com.yangs.kedaquan.R;

/**
 * Created by yangs on 2017/4/28.
 */
public class FrescoImageLoader implements ImageLoader {


    public FrescoImageLoader() {
    }


    @Override
    public void displayImage(Activity activity, Context context, String path, final GalleryImageView imageView, int width, int height) {


        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
                .setFadeDuration(300)
                .setPlaceholderImage(R.drawable.pick)   // 占位图
                .setFailureImage(R.drawable.pick)       // 加载失败图
                .setProgressBarImage(new ProgressBarDrawable())     // loading
                .build();

        final DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchy, context);
        imageView.setOnImageViewListener(new GalleryImageView.OnImageViewListener() {
            @Override
            public void onDraw(Canvas canvas) {
                Drawable drawable = draweeHolder.getHierarchy().getTopLevelDrawable();
                if (drawable == null) {
                    imageView.setImageResource(R.drawable.pick);
                } else {
                    imageView.setImageDrawable(drawable);
                }
            }


            @Override
            public boolean verifyDrawable(Drawable dr) {
                return dr == draweeHolder.getHierarchy().getTopLevelDrawable();
            }

            @Override
            public void onDetach() {
                draweeHolder.onDetach();
            }

            @Override
            public void onAttach() {
                draweeHolder.onAttach();
            }
        });

        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(path)
                .build();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeHolder.getController())
                .setImageRequest(imageRequest)
                .build();
        draweeHolder.setController(controller);


    }

    @Override
    public void clearMemoryCache() {

    }
}