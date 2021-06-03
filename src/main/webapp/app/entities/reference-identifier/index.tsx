import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ReferenceIdentifier from './reference-identifier';
import ReferenceIdentifierDetail from './reference-identifier-detail';
import ReferenceIdentifierUpdate from './reference-identifier-update';
import ReferenceIdentifierDeleteDialog from './reference-identifier-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReferenceIdentifierUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReferenceIdentifierUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReferenceIdentifierDetail} />
      <ErrorBoundaryRoute path={match.url} component={ReferenceIdentifier} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReferenceIdentifierDeleteDialog} />
  </>
);

export default Routes;
