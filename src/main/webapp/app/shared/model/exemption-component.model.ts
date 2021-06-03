import dayjs from 'dayjs';
import { ICostToBeneficiaryComponent } from 'app/shared/model/cost-to-beneficiary-component.model';
import { ExemptionTypeEnum } from 'app/shared/model/enumerations/exemption-type-enum.model';

export interface IExemptionComponent {
  id?: number;
  type?: ExemptionTypeEnum;
  start?: string | null;
  end?: string | null;
  costToBeneficiary?: ICostToBeneficiaryComponent | null;
}

export const defaultValue: Readonly<IExemptionComponent> = {};
