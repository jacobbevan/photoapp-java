package com.jacobbevan.photoapp.utility;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.jacobbevan.photoapp.model.ImageSummary;
import org.apache.tomcat.jni.Local;
import org.imgscalr.Scalr;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.imgscalr.Scalr.crop;
import static org.imgscalr.Scalr.resize;

public class ImageTransform {

/*
        private static Regex _regPixels = new Regex( @"(?<size>[0-9]+)\spixels");
        public static string SeekTag(IEnumerable<MetadataExtractor.Directory> results,  string directory, string tagName)
        {
            return results.FirstOrDefault(d=>d.Name == directory)?.Tags.FirstOrDefault(f=>f.Name==tagName)?.Description;
        }

        public static int? SeekPixels(IEnumerable<MetadataExtractor.Directory> results, string directory, string tagName)
    {
        var text = SeekTag(results, directory, tagName);

        if(string.IsNullOrEmpty(text))
        {
            return null;
        }

        var match = _regPixels.Match(text);
        if(match.Success)
        {
            var size = match.Groups.FirstOrDefault(g=>g.Name == "size")?.Value;

            return System.Int32.Parse(size);

        }

        return null;

    }

        public static int? SeekHeight(IEnumerable<MetadataExtractor.Directory> results)
    {
        return SeekPixels(results, "JPEG", "Image Height");
    }

        public static int? SeekWidth(IEnumerable<MetadataExtractor.Directory> results)
    {
        return SeekPixels(results, "JPEG", "Image Width");
    }

        public static DateTime? SeekCreated(IEnumerable<MetadataExtractor.Directory> results)
    {
        DateTime result;
        var dateTimeStr = SeekTag(results, "Exif IFD0", "Date/Time");

        if(string.IsNullOrEmpty(dateTimeStr))
            return null;

        if(DateTime.TryParseExact(dateTimeStr, "yyyy:MM:dd HH:mm:ss",CultureInfo.InvariantCulture,DateTimeStyles.None, out result))
            return result;

        return null;
    }


        public static ImageSummary CreateImageSummary(string id, Stream image)
        {
            //height 1
            //width 3

            // "QuickTime Movie Header"
            // "Created" : "Tue Apr 25 15:56:01 2017"
            //

            //"JPEG"
            // "Image Height" : "3264 pixels"
            // "Image Width"

            //"Exif IFD0"
            //"Date/Time" "2016:02:01 03:50:27"

            //"Exif SubIFD"
            //  "Date/Time Original" : "2016:02:01 03:50:27"

            var results = MetadataExtractor.ImageMetadataReader.ReadMetadata(image);

            var height = SeekHeight(results);
            var width = SeekWidth(results);
            var created = SeekCreated(results);

            List<string> captionElements = new List<string>();

            if(created != null)
            {
                captionElements.Add(created.Value.ToString("dd-MMM-yy"));
            }
            if(height != null && width != null)
            {
                captionElements.Add($"{width.Value}x{height.Value}");
            }


            return new ImageSummary
            {
                Id = id,
                        Height = SeekHeight(results),
                        Width = SeekWidth(results),
                        Created = created ?? DateTime.Now,
                    Caption = string.Join(" ", captionElements)
            };
        }
*/

    public static ImageSummary createImageSummary(String id, byte[] image) throws ImageProcessingException, IOException, MetadataException {
        var stream = new ByteArrayInputStream(image);
        Metadata metadata = ImageMetadataReader.readMetadata(stream);

        var jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);

        var height = jpegDirectory.getImageHeight();
        var width = jpegDirectory.getImageWidth();
        var directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        var date = convertToLocalDateTimeViaInstant(directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));

        return new ImageSummary(id, "", date, date, width, height);
    }


    private static ZonedDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault());
    }


    public static byte[] createThumbnail(byte[] imgData) throws IOException {

        var img = imageFromArray(imgData);

        var height = img.getHeight();
        var width = img.getWidth();

        var side = Math.min(height, width);
        var x  = (width - side) /2;
        var y  = (height - side) /2;

        var cropped = crop(img, x, y, side, side);

        var scaled = resize(cropped, Scalr.Method.ULTRA_QUALITY, 400);

        var writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        try(
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                MemoryCacheImageOutputStream memCache = new MemoryCacheImageOutputStream(outStream)
        ) {
            var iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(1);   // an integer between 0 and 1
            writer.setOutput(memCache);
            IIOImage image = new IIOImage(scaled, null, null);
            writer.write(null, image, iwp);
            outStream.flush();
            return outStream.toByteArray();
        }
        finally {
            writer.dispose();
        }
    }

    private static BufferedImage imageFromArray(byte[] data) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        return ImageIO.read(bis);
    }
}
