package com.eleicao.ptep.coud;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author hallef
 */
@Component
public class StorageCloudnary {

    private Map configOpenCloudinary() {
        Map chave = ObjectUtils.asMap(
                "cloud_name", "sud",
                "api_key", "414869814418293",
                "api_secret", "mWG1plNyyL8ufVQEiNNF9NnIcZw");
        return chave;
    }

    public void uploadFoto(byte[] dataImage,Long candidatoId) throws IOException {
        Map conf = ObjectUtils.asMap("public_id", "candidato/"+candidatoId, "transformation", new Transformation().width(90).height(90).gravity("face").crop("fill").radius("max"));
        Cloudinary cloudinary = new Cloudinary(configOpenCloudinary());
        cloudinary.uploader().upload(dataImage, conf);
    }
}
