import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OperationOutcome from './operation-outcome';
import OperationOutcomeDetail from './operation-outcome-detail';
import OperationOutcomeUpdate from './operation-outcome-update';
import OperationOutcomeDeleteDialog from './operation-outcome-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OperationOutcomeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OperationOutcomeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OperationOutcomeDetail} />
      <ErrorBoundaryRoute path={match.url} component={OperationOutcome} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OperationOutcomeDeleteDialog} />
  </>
);

export default Routes;
