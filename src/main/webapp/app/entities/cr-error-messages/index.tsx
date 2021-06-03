import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CRErrorMessages from './cr-error-messages';
import CRErrorMessagesDetail from './cr-error-messages-detail';
import CRErrorMessagesUpdate from './cr-error-messages-update';
import CRErrorMessagesDeleteDialog from './cr-error-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CRErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CRErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CRErrorMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={CRErrorMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CRErrorMessagesDeleteDialog} />
  </>
);

export default Routes;
