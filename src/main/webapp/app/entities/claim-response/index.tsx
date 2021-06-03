import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClaimResponse from './claim-response';
import ClaimResponseDetail from './claim-response-detail';
import ClaimResponseUpdate from './claim-response-update';
import ClaimResponseDeleteDialog from './claim-response-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClaimResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClaimResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClaimResponseDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClaimResponse} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClaimResponseDeleteDialog} />
  </>
);

export default Routes;
