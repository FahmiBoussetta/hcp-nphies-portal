import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DiagnosisSequence from './diagnosis-sequence';
import DiagnosisSequenceDetail from './diagnosis-sequence-detail';
import DiagnosisSequenceUpdate from './diagnosis-sequence-update';
import DiagnosisSequenceDeleteDialog from './diagnosis-sequence-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DiagnosisSequenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DiagnosisSequenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DiagnosisSequenceDetail} />
      <ErrorBoundaryRoute path={match.url} component={DiagnosisSequence} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DiagnosisSequenceDeleteDialog} />
  </>
);

export default Routes;
