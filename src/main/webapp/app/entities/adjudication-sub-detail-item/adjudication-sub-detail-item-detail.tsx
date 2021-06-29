import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './adjudication-sub-detail-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationSubDetailItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adjudicationSubDetailItemEntity = useAppSelector(state => state.adjudicationSubDetailItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adjudicationSubDetailItemDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailItem.detail.title">AdjudicationSubDetailItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adjudicationSubDetailItemEntity.id}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailItem.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{adjudicationSubDetailItemEntity.sequence}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudicationSubDetailItem.adjudicationDetailItem">Adjudication Detail Item</Translate>
          </dt>
          <dd>{adjudicationSubDetailItemEntity.adjudicationDetailItem ? adjudicationSubDetailItemEntity.adjudicationDetailItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adjudication-sub-detail-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjudication-sub-detail-item/${adjudicationSubDetailItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdjudicationSubDetailItemDetail;
