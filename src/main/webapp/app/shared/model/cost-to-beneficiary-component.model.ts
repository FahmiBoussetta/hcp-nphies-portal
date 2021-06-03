import { IExemptionComponent } from 'app/shared/model/exemption-component.model';
import { ICoverage } from 'app/shared/model/coverage.model';
import { CostToBeneficiaryTypeEnum } from 'app/shared/model/enumerations/cost-to-beneficiary-type-enum.model';

export interface ICostToBeneficiaryComponent {
  id?: number;
  type?: CostToBeneficiaryTypeEnum | null;
  isMoney?: boolean | null;
  value?: number;
  exceptions?: IExemptionComponent[] | null;
  coverage?: ICoverage | null;
}

export const defaultValue: Readonly<ICostToBeneficiaryComponent> = {
  isMoney: false,
};
