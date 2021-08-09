package com.eleicao.ptep.coud;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
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
    
    public void uploadFoto(byte[] dataImage,String candidatoId) throws IOException {
        Map conf = ObjectUtils.asMap("public_id", "candidato/"+candidatoId);
        Cloudinary cloudinary = new Cloudinary(configOpenCloudinary());
        cloudinary.uploader().upload(dataImage, conf);
    }
    
    public void deleteFoto(String candidatoId) throws IOException {
        if(!StringUtils.isBlank(candidatoId)) {
            Map confCandidato = ObjectUtils.asMap("public_id","candidato/"+candidatoId);
            Cloudinary cloudinary = new Cloudinary(configOpenCloudinary());
            cloudinary.uploader().destroy("public_id",confCandidato);
        }
    }
}






//    private void uploadFotoThumbnail(byte[] dataImage,String candidatoId) throws IOException {
//        Map conf = ObjectUtils.asMap("public_id", "candidato-thumbnail/"+candidatoId, "transformation", new Transformation().gravity("face").height(40).width(40).crop("crop").chain().radius("max").chain().width(100).crop("scale"));
//        Cloudinary cloudinary = new Cloudinary(configOpenCloudinary());
//        cloudinary.uploader().upload(dataImage, conf);
//    }