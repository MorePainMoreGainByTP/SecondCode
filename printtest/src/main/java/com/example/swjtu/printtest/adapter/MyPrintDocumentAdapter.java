package com.example.swjtu.printtest.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by tangpeng on 2017/6/1.
 */

public class MyPrintDocumentAdapter extends PrintDocumentAdapter {

    private static final String TAG = "MyPrintDocumentAdapter";

    private Context context;
    private PrintedPdfDocument mPdfDocument;
    private List<List<String>> contents;

    private int totalPages; //总的页数
    private int itemsPerPage = 10;

    public MyPrintDocumentAdapter(Context context, List<List<String>> contents) {
        this.context = context;
        this.contents = contents;
    }

    /**
     * 用于设置将要打印的页面数
     * including the number of pages and content type.
     *
     * @param oldAttributes
     * @param newAttributes
     * @param cancellationSignal
     * @param callback
     * @param extras
     */
    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        // Create a new PdfDocument with the requested page attributes
        mPdfDocument = new PrintedPdfDocument(context, newAttributes);
        // Respond to cancellation request
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }
        // Compute the expected number of printed pages
        int pages = totalPages = computePageCount(newAttributes);

        if (pages > 0) {
            // Return print information to print framework
            PrintDocumentInfo info = new PrintDocumentInfo
                    .Builder("print_output_tp.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(pages)
                    .build();
            // Content layout reflow is complete
            callback.onLayoutFinished(info, true);
        } else {
            // Otherwise report an error to the print framework
            callback.onLayoutFailed("Page count calculation failed.");
        }
    }

    private int computePageCount(PrintAttributes printAttributes) {
        //int itemsPerPage = 4; // default item count for portrait mode

        PrintAttributes.MediaSize pageSize = printAttributes.getMediaSize();
        if (!pageSize.isPortrait()) {
            // Six items per page in landscape orientation
            itemsPerPage = 14;
        }

        // Determine number of print items
        int printItemCount = contents.size();

        return (int) Math.ceil(1.0 * printItemCount / itemsPerPage);
    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        // Iterate over each page of the document,
        // check if it's in the output range.
        for (int i = 0; i < totalPages; i++) {
            // Check to see if this page is in the output range.
            PdfDocument.Page page = mPdfDocument.startPage(i);
            // check for cancellation
            if (cancellationSignal.isCanceled()) {
                callback.onWriteCancelled();
                mPdfDocument.close();
                mPdfDocument = null;
                return;
            }
            // Draw page content for printing
            drawPage(page, i * itemsPerPage);
            // Rendering is complete, so page can be finalized.
            mPdfDocument.finishPage(page);
//
//            if (containsPage(pageRanges, i)) {
//                // If so, add it to writtenPagesArray. writtenPagesArray.size()
//                // is used to compute the next output page index.
//                writtenPagesArray.append(writtenPagesArray.size(), i);
//            }
        }

        // Write PDF document to file
        try {
            mPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            mPdfDocument.close();
            mPdfDocument = null;
        }
        // PageRange[] writtenPages = computeWrittenPages();
        // Signal the print framework the document is complete
        callback.onWriteFinished(pageRanges);
    }

    private void drawPage(PdfDocument.Page page, int index) {
        Canvas canvas = page.getCanvas();

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Log.i(TAG, " height:" + height + ",width:" + width);
        Log.i(TAG, " MaximumBitmapHeight:" + canvas.getMaximumBitmapHeight() + ",MaximumBitmapWidth:" + canvas.getMaximumBitmapWidth());
        if (index + itemsPerPage >= contents.size()) {
            for (int i = 0; i < contents.size() - index; i++) {
                drawRectAndText(canvas, 50 * (i + 1), 50, contents.get(index + i));
            }
        } else {
            for (int i = 0; i < 4; i++) {
                drawRectAndText(canvas, 50 * (i + 1), 50, contents.get(index + i));
            }
        }

        // units are in points (1/72 of an inch)
        /*
        int titleBaseLine = 72;
        int leftMargin = 54;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(36);
        canvas.drawText("Test Title", leftMargin, titleBaseLine, paint);

        paint.setTextSize(11);
        canvas.drawText("Test paragraph", leftMargin, titleBaseLine + 25, paint);

        paint.setColor(Color.BLUE);
        canvas.drawRect(100, 100, 172, 172, paint);
        */
    }

    private void drawRectAndText(Canvas canvas, int height, int spec, List<String> text) {
        Log.i(TAG, "drawRectAndText: " + text);
        int width = canvas.getWidth() / 6;
        Paint paint = new Paint();
        paint.setTextSize(11);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < text.size(); i++) {
            canvas.drawRect(width * i, height, width * (i + 1), height + spec, paint);
            canvas.drawText(text.get(i), width * i + width / 2, height + spec / 2, paint);
        }

    }

    private void drawHeader(Canvas canvas) {

    }

}
