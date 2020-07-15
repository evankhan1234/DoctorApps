package com.nextgenit.doctor.Network;

import com.nextgenit.doctor.NetworkModel.DiagnosisListReponses;
import com.nextgenit.doctor.NetworkModel.InvestigationListResponses;
import com.nextgenit.doctor.NetworkModel.LoginResponses;
import com.nextgenit.doctor.NetworkModel.MedicationListResponses;
import com.nextgenit.doctor.NetworkModel.PatientListResponses;
import com.nextgenit.doctor.NetworkModel.PharmacyListResponses;
import com.nextgenit.doctor.NetworkModel.RegistrationResponses;
import com.nextgenit.doctor.NetworkModel.SpecialistResponses;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IRetrofitApi {
    //    @GET("setupdata/studyclass")
//    io.reactivex.Observable<StudyClassResponses> getStudyClass();
//
//    io.reactivex.Observable<MedicineResponses> getMedicines();
//    @POST("user/login")
    @POST("auth/patient-list")
    io.reactivex.Observable<PatientListResponses> getPatientListAll();
    @FormUrlEncoded
    @POST("auth/patient-list")
    io.reactivex.Observable<PatientListResponses> getPatientList(@Field("pharmacy_id") int pharmacy_id);
    @FormUrlEncoded
    @POST("auth/item-list-by-code")
    io.reactivex.Observable<InvestigationListResponses> getInvestigationList(@Field("item_type") String item_type);

    @FormUrlEncoded
    @POST("auth/pres-lookup-list-by-code")
    io.reactivex.Observable<DiagnosisListReponses> getDiagnosisList(@Field("lookup_code") String lookup_code);
    @FormUrlEncoded
    @POST("auth/item-list-by-code")
    io.reactivex.Observable<MedicationListResponses> getMedicationList(@Field("item_type") String item_type);
    @POST("auth/pharmacy-list")
    io.reactivex.Observable<PharmacyListResponses> getPharmacy();
    @GET("auth/spl-wise-doc-reg")
    io.reactivex.Observable<SpecialistResponses> getSpecialList();
    @FormUrlEncoded
    @POST("auth/login")
    io.reactivex.Observable<LoginResponses> postLogin(@Field("email") String email,
                                                      @Field("password") String password);
    @FormUrlEncoded
    @POST("auth/doctor-reg")
    io.reactivex.Observable<RegistrationResponses> postRegistration(@Field("doc_name") String doc_name,
                                                                    @Field("doc_email") String doc_email,
                                                                    @Field("doc_mobile") String doc_mobile,
                                                                    @Field("doc_pass") String doc_pass,
                                                                    @Field("doc_re_pass") String doc_re_pass,
                                                                    @Field("specialization_id") int specialization_id);

}
