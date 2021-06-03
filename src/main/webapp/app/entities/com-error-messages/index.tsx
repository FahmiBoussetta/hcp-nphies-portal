import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ComErrorMessages from './com-error-messages';
import ComErrorMessagesDetail from './com-error-messages-detail';
import ComErrorMessagesUpdate from './com-error-messages-update';
import ComErrorMessagesDeleteDialog from './com-error-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ComErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ComErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ComErrorMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={ComErrorMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ComErrorMessagesDeleteDialog} />
  </>
);

export default Routes;
