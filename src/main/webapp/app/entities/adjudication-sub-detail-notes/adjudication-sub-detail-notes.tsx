import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './adjudication-sub-detail-notes.reducer';
import { IAdjudicationSubDetailNotes } from 'app/shared/model/adjudication-sub-detail-notes.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationSubDetailNotes = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const adjudicationSubDetailNotesList = useAppSelector(state => state.adjudicationSubDetailNotes.entities);
  const loading = useAppSelector(state => state.adjudicationSubDetailNotes.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="adjudication-sub-detail-notes-heading" data-cy="AdjudicationSubDetailNotesHeading">
        <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.home.title">Adjudication Sub Detail Notes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.home.createLabel">
              Create new Adjudication Sub Detail Notes
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {adjudicationSubDetailNotesList && adjudicationSubDetailNotesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.note">Note</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.adjudicationSubDetailItem">
                    Adjudication Sub Detail Item
                  </Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adjudicationSubDetailNotesList.map((adjudicationSubDetailNotes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${adjudicationSubDetailNotes.id}`} color="link" size="sm">
                      {adjudicationSubDetailNotes.id}
                    </Button>
                  </td>
                  <td>{adjudicationSubDetailNotes.note}</td>
                  <td>
                    {adjudicationSubDetailNotes.adjudicationSubDetailItem ? (
                      <Link to={`adjudication-sub-detail-item/${adjudicationSubDetailNotes.adjudicationSubDetailItem.id}`}>
                        {adjudicationSubDetailNotes.adjudicationSubDetailItem.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${adjudicationSubDetailNotes.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${adjudicationSubDetailNotes.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${adjudicationSubDetailNotes.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailNotes.home.notFound">
                No Adjudication Sub Detail Notes found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AdjudicationSubDetailNotes;
