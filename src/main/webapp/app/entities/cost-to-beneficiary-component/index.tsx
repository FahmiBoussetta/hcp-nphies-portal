import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CostToBeneficiaryComponent from './cost-to-beneficiary-component';
import CostToBeneficiaryComponentDetail from './cost-to-beneficiary-component-detail';
import CostToBeneficiaryComponentUpdate from './cost-to-beneficiary-component-update';
import CostToBeneficiaryComponentDeleteDialog from './cost-to-beneficiary-component-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CostToBeneficiaryComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CostToBeneficiaryComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CostToBeneficiaryComponentDetail} />
      <ErrorBoundaryRoute path={match.url} component={CostToBeneficiaryComponent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CostToBeneficiaryComponentDeleteDialog} />
  </>
);

export default Routes;
