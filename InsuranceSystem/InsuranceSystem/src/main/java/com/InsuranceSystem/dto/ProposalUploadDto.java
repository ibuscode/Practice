package com.InsuranceSystem.dto;

import com.InsuranceSystem.enums.ProposalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProposalUploadDto(
        @NotNull(message = "Quote ID is mandatory")
        int quoteId,
        ProposalStatus proposalStatus,
        int VehicleId


        /*@NotBlank(message = "RC Book path is mandatory")
        String rcBookPath,

        @NotBlank(message = "Driving License path is mandatory")
        String drivingLicensePath,

        @NotBlank(message = "Vehicle Photo path is mandatory")
        String vehiclePhotoPath*/
) {
}
