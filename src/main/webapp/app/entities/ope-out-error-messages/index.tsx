import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OpeOutErrorMessages from './ope-out-error-messages';
import OpeOutErrorMessagesDetail from './ope-out-error-messages-detail';
import OpeOutErrorMessagesUpdate from './ope-out-error-messages-update';
import OpeOutErrorMessagesDeleteDialog from './ope-out-error-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OpeOutErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OpeOutErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OpeOutErrorMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={OpeOutErrorMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OpeOutErrorMessagesDeleteDialog} />
  </>
);

export default Routes;
