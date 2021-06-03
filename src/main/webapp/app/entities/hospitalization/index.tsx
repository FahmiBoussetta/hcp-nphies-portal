import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Hospitalization from './hospitalization';
import HospitalizationDetail from './hospitalization-detail';
import HospitalizationUpdate from './hospitalization-update';
import HospitalizationDeleteDialog from './hospitalization-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HospitalizationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HospitalizationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HospitalizationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Hospitalization} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HospitalizationDeleteDialog} />
  </>
);

export default Routes;
