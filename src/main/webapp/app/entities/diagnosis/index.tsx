import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Diagnosis from './diagnosis';
import DiagnosisDetail from './diagnosis-detail';
import DiagnosisUpdate from './diagnosis-update';
import DiagnosisDeleteDialog from './diagnosis-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DiagnosisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DiagnosisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DiagnosisDetail} />
      <ErrorBoundaryRoute path={match.url} component={Diagnosis} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DiagnosisDeleteDialog} />
  </>
);

export default Routes;
