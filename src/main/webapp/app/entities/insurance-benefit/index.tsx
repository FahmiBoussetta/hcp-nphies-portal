import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InsuranceBenefit from './insurance-benefit';
import InsuranceBenefitDetail from './insurance-benefit-detail';
import InsuranceBenefitUpdate from './insurance-benefit-update';
import InsuranceBenefitDeleteDialog from './insurance-benefit-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InsuranceBenefitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InsuranceBenefitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InsuranceBenefitDetail} />
      <ErrorBoundaryRoute path={match.url} component={InsuranceBenefit} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InsuranceBenefitDeleteDialog} />
  </>
);

export default Routes;
