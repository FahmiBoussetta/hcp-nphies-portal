import dayjs from 'dayjs';
import { IDiagnosisSequence } from 'app/shared/model/diagnosis-sequence.model';
import { IInformationSequence } from 'app/shared/model/information-sequence.model';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { IDetailItem } from 'app/shared/model/detail-item.model';
import { IClaim } from 'app/shared/model/claim.model';
import { BodySiteEnum } from 'app/shared/model/enumerations/body-site-enum.model';
import { SubSiteEnum } from 'app/shared/model/enumerations/sub-site-enum.model';

export interface IItem {
  id?: number;
  sequence?: number;
  isPackage?: boolean | null;
  tax?: number | null;
  payerShare?: number | null;
  patientShare?: number;
  careTeamSequence?: number;
  transportationSRCA?: string | null;
  imaging?: string | null;
  laboratory?: string | null;
  medicalDevice?: string | null;
  oralHealthIP?: string | null;
  oralHealthOP?: string | null;
  procedure?: string | null;
  services?: string | null;
  medicationCode?: string | null;
  servicedDate?: string | null;
  servicedDateStart?: string | null;
  servicedDateEnd?: string | null;
  quantity?: number;
  unitPrice?: number;
  factor?: number | null;
  bodySite?: BodySiteEnum | null;
  subSite?: SubSiteEnum | null;
  diagnosisSequences?: IDiagnosisSequence[] | null;
  informationSequences?: IInformationSequence[] | null;
  udis?: IReferenceIdentifier[] | null;
  details?: IDetailItem[] | null;
  claim?: IClaim | null;
}

export const defaultValue: Readonly<IItem> = {
  isPackage: false,
};
