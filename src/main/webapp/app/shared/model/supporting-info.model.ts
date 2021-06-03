import dayjs from 'dayjs';
import { IQuantity } from 'app/shared/model/quantity.model';
import { IAttachment } from 'app/shared/model/attachment.model';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { IClaim } from 'app/shared/model/claim.model';
import { SupportingInfoCategoryEnum } from 'app/shared/model/enumerations/supporting-info-category-enum.model';
import { SupportingInfoCodeVisitEnum } from 'app/shared/model/enumerations/supporting-info-code-visit-enum.model';
import { SupportingInfoCodeFdiEnum } from 'app/shared/model/enumerations/supporting-info-code-fdi-enum.model';
import { SupportingInfoReasonEnum } from 'app/shared/model/enumerations/supporting-info-reason-enum.model';
import { SupportingInfoReasonMissingToothEnum } from 'app/shared/model/enumerations/supporting-info-reason-missing-tooth-enum.model';

export interface ISupportingInfo {
  id?: number;
  sequence?: number | null;
  codeLOINC?: string | null;
  category?: SupportingInfoCategoryEnum | null;
  codeVisit?: SupportingInfoCodeVisitEnum | null;
  codeFdiOral?: SupportingInfoCodeFdiEnum | null;
  timing?: string | null;
  timingEnd?: string | null;
  valueBoolean?: boolean | null;
  valueString?: string | null;
  reason?: SupportingInfoReasonEnum | null;
  reasonMissingTooth?: SupportingInfoReasonMissingToothEnum | null;
  valueQuantity?: IQuantity | null;
  valueAttachment?: IAttachment | null;
  valueReference?: IReferenceIdentifier | null;
  claim?: IClaim | null;
}

export const defaultValue: Readonly<ISupportingInfo> = {
  valueBoolean: false,
};
