import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AckErrorMessages from './ack-error-messages';
import AckErrorMessagesDetail from './ack-error-messages-detail';
import AckErrorMessagesUpdate from './ack-error-messages-update';
import AckErrorMessagesDeleteDialog from './ack-error-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AckErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AckErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AckErrorMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={AckErrorMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AckErrorMessagesDeleteDialog} />
  </>
);

export default Routes;
