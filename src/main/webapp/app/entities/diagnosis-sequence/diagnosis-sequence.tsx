import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './diagnosis-sequence.reducer';
import { IDiagnosisSequence } from 'app/shared/model/diagnosis-sequence.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DiagnosisSequence = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const diagnosisSequenceList = useAppSelector(state => state.diagnosisSequence.entities);
  const loading = useAppSelector(state => state.diagnosisSequence.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="diagnosis-sequence-heading" data-cy="DiagnosisSequenceHeading">
        <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.home.title">Diagnosis Sequences</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.home.createLabel">Create new Diagnosis Sequence</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {diagnosisSequenceList && diagnosisSequenceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.diagSeq">Diag Seq</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.item">Item</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {diagnosisSequenceList.map((diagnosisSequence, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${diagnosisSequence.id}`} color="link" size="sm">
                      {diagnosisSequence.id}
                    </Button>
                  </td>
                  <td>{diagnosisSequence.diagSeq}</td>
                  <td>{diagnosisSequence.item ? <Link to={`item/${diagnosisSequence.item.id}`}>{diagnosisSequence.item.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${diagnosisSequence.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${diagnosisSequence.id}/edit`}
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
                        to={`${match.url}/${diagnosisSequence.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.home.notFound">No Diagnosis Sequences found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DiagnosisSequence;
