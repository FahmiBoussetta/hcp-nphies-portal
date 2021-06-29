import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './adjudication.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adjudicationEntity = useAppSelector(state => state.adjudication.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adjudicationDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.adjudication.detail.title">Adjudication</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adjudicationEntity.id}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="hcpNphiesPortalApp.adjudication.category">Category</Translate>
            </span>
          </dt>
          <dd>{adjudicationEntity.category}</dd>
          <dt>
            <span id="reason">
              <Translate contentKey="hcpNphiesPortalApp.adjudication.reason">Reason</Translate>
            </span>
          </dt>
          <dd>{adjudicationEntity.reason}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="hcpNphiesPortalApp.adjudication.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{adjudicationEntity.amount}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="hcpNphiesPortalApp.adjudication.value">Value</Translate>
            </span>
          </dt>
          <dd>{adjudicationEntity.value}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudication.adjudicationItem">Adjudication Item</Translate>
          </dt>
          <dd>{adjudicationEntity.adjudicationItem ? adjudicationEntity.adjudicationItem.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudication.adjudicationDetailItem">Adjudication Detail Item</Translate>
          </dt>
          <dd>{adjudicationEntity.adjudicationDetailItem ? adjudicationEntity.adjudicationDetailItem.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudication.adjudicationSubDetailItem">Adjudication Sub Detail Item</Translate>
          </dt>
          <dd>{adjudicationEntity.adjudicationSubDetailItem ? adjudicationEntity.adjudicationSubDetailItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adjudication" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjudication/${adjudicationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdjudicationDetail;
