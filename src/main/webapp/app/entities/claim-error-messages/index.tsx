import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClaimErrorMessages from './claim-error-messages';
import ClaimErrorMessagesDetail from './claim-error-messages-detail';
import ClaimErrorMessagesUpdate from './claim-error-messages-update';
import ClaimErrorMessagesDeleteDialog from './claim-error-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClaimErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClaimErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClaimErrorMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClaimErrorMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClaimErrorMessagesDeleteDialog} />
  </>
);

export default Routes;
